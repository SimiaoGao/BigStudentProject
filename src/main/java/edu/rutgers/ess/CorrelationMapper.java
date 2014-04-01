package edu.rutgers.ess;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import edu.rutgers.ess.utility.Grade;
import edu.rutgers.ess.utility.YearTermOfStudy;

public class CorrelationMapper extends TableMapper<Text, Text> {

	private final static String TABLE_FAMILY = "c";
	private final static String PAIRS_YT_GRADE = "YT_GRADE";

	private static final Logger log = Logger.getLogger(CorrelationMapper.class.getName());
	
	public void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {

		String[] keys = Bytes.toString(key.get()).split(",");
		String val = new String(value.getValue(Bytes.toBytes(TABLE_FAMILY), Bytes.toBytes(PAIRS_YT_GRADE)));
		String[] vals = val.split(",");

		String testRUID = keys[0];
		String trainingRUID = keys[1];
		String course = keys[2];

		YearTermOfStudy testingYearTermOfStudy = YearTermOfStudy.NULL;
		try {
			testingYearTermOfStudy = YearTermOfStudy.transform(vals[0], vals[1], vals[2], vals[3]);
		} catch (IllegalArgumentException iae) {
			log.info("year term out of range for " + course + ". Admit Year: " + vals[0] + " Term: " + vals[1] + ". Course Taken Year: " + vals[2]
					+ " Course Taken Term: " + vals[3]);
			return;
		} catch (NullPointerException npe) {
			log.info("Unknown year term for " + course + ". Admit Year: " + vals[0] + " Term: " + vals[1] + ". Course Taken Year: " + vals[2]
					+ " Course Taken Term: " + vals[3]);
			return;
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			log.info("Value missing for " + val);
		}

		Grade testGrade = Grade.NULL;
		try {
			testGrade = Grade.fromString(vals[4]);
		} catch (IllegalArgumentException iae) {
			log.info("unknown grade: " + vals[4] + ". Student " + trainingRUID + " received for " + course);
			return;
		} catch (NullPointerException npe) {
			log.info(vals[4] + " grade. Student " + trainingRUID + " received for " + course);
			return;
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			log.info("Value missing for " + val);
		}

		YearTermOfStudy trainingYearTermOfStudy = YearTermOfStudy.NULL;
		try {
			trainingYearTermOfStudy = YearTermOfStudy.transform(vals[5], vals[6], vals[7], vals[8]);
		} catch (IllegalArgumentException iae) {
			log.info("year term out of range for " + course + ". Admit Year: " + vals[5] + " Term: " + vals[6] + ". Course Taken Year: " + vals[7]
					+ " Course Taken Term: " + vals[8]);
			return;
		} catch (NullPointerException npe) {
			log.info("Unknown year term for " + course + ". Admit Year: " + vals[5] + " Term: " + vals[6] + ". Course Taken Year: " + vals[7]
					+ " Course Taken Term: " + vals[8]);
			return;
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			log.info("Value missing for " + val);
		}

		Grade trainingGrade = Grade.NULL;
		try {
			trainingGrade = Grade.fromString(vals[9]);
		} catch (IllegalArgumentException iae) {
			log.info("unknown grade: " + vals[9] + ". Student " + testRUID + " received for course " + vals[3]);
			return;
		} catch (NullPointerException npe) {
			log.info(vals[9] + " grade. Student " + testRUID + " received for course " + vals[3]);
			return;
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			log.info("Value missing for " + val);
		}

		double testRating = testingYearTermOfStudy.getWeight() / 12.0 + testGrade.getGradeWeights();
		double trainingRating = trainingYearTermOfStudy.getWeight() / 12.0 + trainingGrade.getGradeWeights();

		context.write(new Text(testRUID + "," + trainingRUID), new Text(course + "," + testRating + "," + trainingRating));

	}
}
