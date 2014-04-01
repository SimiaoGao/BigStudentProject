package edu.rutgers.ess;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class Top50Mapper extends TableMapper<Text, Text> {

	private final static String TABLE_FAMILY = "c";
	private final static String CORRELATION_COLUMN = "correlation";

	private static final Logger log = Logger.getLogger(Top50Mapper.class.getName());
	
	public void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
		
		String[] keys = Bytes.toString(key.get()).split(",");
		String correlation = new String(value.getValue(Bytes.toBytes(TABLE_FAMILY), Bytes.toBytes(CORRELATION_COLUMN)));

		String testRUID = keys[0];
		String trainingRUID = keys[1];
		
		context.write(new Text(testRUID), new Text(correlation + "," + trainingRUID));
	}
}
