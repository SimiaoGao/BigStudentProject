package edu.rutgers.ess;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

public class CorrelationReducer extends TableReducer<Text, Text, ImmutableBytesWritable> {

	private final static String TABLE_FAMILY = "c";
	private final static String CORRELATION_COLUMN = "correlation";
	private final static String CORRELATION_COURSES = "courses";

	private static final Logger log = Logger.getLogger(CorrelationReducer.class.getName());
	
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		String[] keys = key.toString().split(",");
		String testRUID = keys[0];
		String trainingRUID = keys[1];

		StringBuilder courses = new StringBuilder();

		double sumXY = 0.0, sumX = 0.0, sumY = 0.0, sumXX = 0.0, sumYY = 0.0, n = 0.0;
		double correlation = 0.0;
		String delimiter = "";

		for (Text t : values) {

			String[] vals = t.toString().split(",");
			double x = Double.parseDouble(vals[1]);
			double y = Double.parseDouble(vals[2]);
			courses.append(delimiter + vals[0]);
			delimiter = ",";

			sumXY += x * y;
			sumYY += y * y;
			sumXX += x * x;
			sumY += y;
			sumX += x;
			n += 1;
		}

		correlation = (n * sumXY - (sumX * sumY)) / (Math.sqrt(n * sumXX - (sumX * sumX)) * Math.sqrt(n * sumYY - (sumY * sumY)));
		if ((Math.sqrt(n * sumXX - (sumX * sumX)) * Math.sqrt(n * sumYY - (sumY * sumY))) == 0)
			correlation = 0;

		Put put = new Put(Bytes.toBytes(testRUID+','+trainingRUID));
		put.add(Bytes.toBytes(TABLE_FAMILY), Bytes.toBytes(CORRELATION_COLUMN), Bytes.toBytes(String.valueOf(correlation)));
		put.add(Bytes.toBytes(TABLE_FAMILY), Bytes.toBytes(CORRELATION_COURSES), Bytes.toBytes(courses.toString()));

		context.write(null, put);
	}
}
