package edu.rutgers.ess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

public class Top50Reducer extends TableReducer<Text, Text, ImmutableBytesWritable> {

	private final static String TABLE_FAMILY = "c";
	private final static String NEIGHBORS_TABLE = "crs_neighbors";
	private final static String NEIGHBORS_COLUMN = "neighbors";
	private final static String CORRELATIONS_COLUMN = "correlations";

	private static final Logger log = Logger.getLogger(Top50Reducer.class.getName());
	
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		
		String testRuid = key.toString();
		List<String> vals = new ArrayList<String>();
		
		for (Text t : values) {
			vals.add(t.toString());
		}
		
		Collections.sort(vals, Collections.reverseOrder());
		
		StringBuilder correlationsSb = new StringBuilder();
		StringBuilder ruidsSb = new StringBuilder();
		String delimiter = "";
		
		for (int i = 0; i < 50 && i < vals.size(); i++) {
			String[] v = vals.get(i).split(",");
			correlationsSb.append(delimiter+v[0]);
			ruidsSb.append(delimiter+v[1]);
			delimiter = ",";
		}
		
		Put put = new Put(Bytes.toBytes(testRuid));
		put.add(Bytes.toBytes(TABLE_FAMILY), Bytes.toBytes(CORRELATIONS_COLUMN), Bytes.toBytes(ruidsSb.toString()));
		put.add(Bytes.toBytes(TABLE_FAMILY), Bytes.toBytes(NEIGHBORS_COLUMN), Bytes.toBytes(correlationsSb.toString()));

		context.write(null, put);
	}
}
