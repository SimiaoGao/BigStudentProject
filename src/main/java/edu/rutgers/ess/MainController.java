package edu.rutgers.ess;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

public class MainController {

	private final static String TABLE_FAMILY = "c";
	private final static String PAIRS_TABLE = "crs_pairs";
	private final static String PAIRS_YT_GRADE = "YT_GRADE";
	private final static String CORRELATION_TABLE = "crs_correlation";
	private final static String CORRELATION_COLUMN = "correlation";
	private final static String COURSES_COLUMN = "courses";
	private final static String NEIGHBORS_TABLE = "crs_neighbors";
	private final static String NEIGHBORS_COLUMN = "neighbors";
	private final static String CORRELATIONS_COLUMN = "correlations";
	
	private static final Logger log = Logger.getLogger(MainController.class.getName());

	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration config = HBaseConfiguration.create();
		Job job = new Job(config, "BigStudentProject");
		job.setJarByClass(CorrelationMapper.class); // class that contains
													// mapper

		Scan scan = new Scan();
		scan.setCaching(500); // 1 is the default in Scan, which will be bad for
		scan.addColumn(Bytes.toBytes(TABLE_FAMILY), Bytes.toBytes(PAIRS_YT_GRADE));
		scan.setCacheBlocks(false); // don't set to true for MR jobs

		TableMapReduceUtil.initTableMapperJob( // input HBase
				PAIRS_TABLE, // table name
				scan, // Scan instance to control CF and attribute selection
				CorrelationMapper.class, // mapper
				Text.class, // mapper output key
				Text.class, // mapper output value
				job);
		TableMapReduceUtil.initTableReducerJob(CORRELATION_TABLE, // output
																	// table
				CorrelationReducer.class, // reducer class
				job);
		job.setNumReduceTasks(16);

		boolean b = job.waitForCompletion(true);
		if (!b) {
			throw new IOException("error with job!");
		}
		
		scan = new Scan();
		scan.setCaching(500); // 1 is the default in Scan, which will be bad for
		scan.addColumn(Bytes.toBytes(TABLE_FAMILY), Bytes.toBytes(CORRELATION_COLUMN));
		scan.setCacheBlocks(false); // don't set to true for MR jobs

		TableMapReduceUtil.initTableMapperJob( // input HBase
				CORRELATION_TABLE, // table name
				scan, // Scan instance to control CF and attribute selection
				Top50Mapper.class, // mapper
				Text.class, // mapper output key
				Text.class, // mapper output value
				job);
		TableMapReduceUtil.initTableReducerJob(NEIGHBORS_TABLE, // output
																	// table
				Top50Reducer.class, // reducer class
				job);
		job.setNumReduceTasks(16);

		b = job.waitForCompletion(true);
		if (!b) {
			throw new IOException("error with job!");
		}
	}
}