package stormapplied.githubcommits;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import stormapplied.githubcommits.topology.CommitFeedListener;
import stormapplied.githubcommits.topology.EmailCounter;
import stormapplied.githubcommits.topology.EmailExtractor;

public class LocalTopologyRunner {
  private static final int TEN_MINUTES = 600000;

  public static void main(String[] args) {
    TopologyBuilder builder = new TopologyBuilder();

    builder.setSpout("commit-feed-listener", new CommitFeedListener());

    builder
        .setBolt("email-extractor", new EmailExtractor())
        .shuffleGrouping("commit-feed-listener");

    builder
        .setBolt("email-counter", new EmailCounter())
        .fieldsGrouping("email-extractor", new Fields("email"));

    Config config = new Config();
    config.setDebug(true);

    StormTopology topology = builder.createTopology();

    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("github-commit-count-topology",
        config,
        topology);

    Utils.sleep(TEN_MINUTES);
    cluster.killTopology("github-commit-count");
    cluster.shutdown();
  }
}