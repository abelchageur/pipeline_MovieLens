// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.

package pipline_complite.tparallelize_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: tParallelize Purpose: <br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class tParallelize implements TalendJob {

	protected static void logIgnoredError(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) {
			cause.printStackTrace();
		}

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "tParallelize";
	private final String projectName = "PIPLINE_COMPLITE";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();
	private RunTrace runTrace = new RunTrace();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					tParallelize.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(tParallelize.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tFileInputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_2_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_3_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tSchemaComplianceCheck_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_4_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_5_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFlowMeter_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_6_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_7_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_8_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFlowMeter_17_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_7_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row11_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row12_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row13_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_3_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tFileInputDelimited_4_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class out3Struct implements routines.system.IPersistableRow<out3Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer DateKey;

		public Integer getDateKey() {
			return this.DateKey;
		}

		public Integer mouth;

		public Integer getMouth() {
			return this.mouth;
		}

		public Integer year;

		public Integer getYear() {
			return this.year;
		}

		public Integer day;

		public Integer getDay() {
			return this.day;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.DateKey = readInteger(dis);

					this.mouth = readInteger(dis);

					this.year = readInteger(dis);

					this.day = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.DateKey = readInteger(dis);

					this.mouth = readInteger(dis);

					this.year = readInteger(dis);

					this.day = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.DateKey, dos);

				// Integer

				writeInteger(this.mouth, dos);

				// Integer

				writeInteger(this.year, dos);

				// Integer

				writeInteger(this.day, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.DateKey, dos);

				// Integer

				writeInteger(this.mouth, dos);

				// Integer

				writeInteger(this.year, dos);

				// Integer

				writeInteger(this.day, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("DateKey=" + String.valueOf(DateKey));
			sb.append(",mouth=" + String.valueOf(mouth));
			sb.append(",year=" + String.valueOf(year));
			sb.append(",day=" + String.valueOf(day));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class out2Struct implements routines.system.IPersistableRow<out2Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public java.util.Date timestamp;

		public java.util.Date getTimestamp() {
			return this.timestamp;
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.timestamp = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.timestamp = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.timestamp, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.timestamp, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("timestamp=" + String.valueOf(timestamp));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class out1Struct implements routines.system.IPersistableRow<out1Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public Float rating;

		public Float getRating() {
			return this.rating;
		}

		public java.util.Date timestamp;

		public java.util.Date getTimestamp() {
			return this.timestamp;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

					this.timestamp = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

					this.timestamp = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

				// java.util.Date

				writeDate(this.timestamp, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

				// java.util.Date

				writeDate(this.timestamp, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("userId=" + String.valueOf(userId));
			sb.append(",movieId=" + String.valueOf(movieId));
			sb.append(",rating=" + String.valueOf(rating));
			sb.append(",timestamp=" + String.valueOf(timestamp));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(out1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public String rating;

		public String getRating() {
			return this.rating;
		}

		public String timestamp;

		public String getTimestamp() {
			return this.timestamp;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					this.rating = readString(dis);

					this.timestamp = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					this.rating = readString(dis);

					this.timestamp = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// String

				writeString(this.rating, dos);

				// String

				writeString(this.timestamp, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// String

				writeString(this.rating, dos);

				// String

				writeString(this.timestamp, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("userId=" + String.valueOf(userId));
			sb.append(",movieId=" + String.valueOf(movieId));
			sb.append(",rating=" + rating);
			sb.append(",timestamp=" + timestamp);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row3Struct row3 = new row3Struct();
				out1Struct out1 = new out1Struct();
				out2Struct out2 = new out2Struct();
				out3Struct out3 = new out3Struct();

				/**
				 * [tUniqRow_1 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.FALSE);

				ok_Hash.put("tUniqRow_1", false);
				start_Hash.put("tUniqRow_1", System.currentTimeMillis());

				currentComponent = "tUniqRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "out3");
				}

				int tos_count_tUniqRow_1 = 0;

				class KeyStruct_tUniqRow_1 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					Integer DateKey;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.DateKey == null) ? 0 : this.DateKey.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_1 other = (KeyStruct_tUniqRow_1) obj;

						if (this.DateKey == null) {
							if (other.DateKey != null)
								return false;

						} else if (!this.DateKey.equals(other.DateKey))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_1 = 0;
				int nb_duplicates_tUniqRow_1 = 0;
				KeyStruct_tUniqRow_1 finder_tUniqRow_1 = new KeyStruct_tUniqRow_1();
				java.util.Set<KeyStruct_tUniqRow_1> keystUniqRow_1 = new java.util.HashSet<KeyStruct_tUniqRow_1>();

				/**
				 * [tUniqRow_1 begin ] stop
				 */

				/**
				 * [tMap_3 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.FALSE);

				ok_Hash.put("tMap_3", false);
				start_Hash.put("tMap_3", System.currentTimeMillis());

				currentComponent = "tMap_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "out2");
				}

				int tos_count_tMap_3 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_3__Struct {
				}
				Var__tMap_3__Struct Var__tMap_3 = new Var__tMap_3__Struct();
// ###############################

// ###############################
// # Outputs initialization
				out3Struct out3_tmp = new out3Struct();
// ###############################

				/**
				 * [tMap_3 begin ] stop
				 */

				/**
				 * [tMap_2 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.FALSE);

				ok_Hash.put("tMap_2", false);
				start_Hash.put("tMap_2", System.currentTimeMillis());

				currentComponent = "tMap_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "out1");
				}

				int tos_count_tMap_2 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_2__Struct {
				}
				Var__tMap_2__Struct Var__tMap_2 = new Var__tMap_2__Struct();
// ###############################

// ###############################
// # Outputs initialization
				out2Struct out2_tmp = new out2Struct();
// ###############################

				/**
				 * [tMap_2 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.FALSE);

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row3");
				}

				int tos_count_tMap_1 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				out1Struct out1_tmp = new out1Struct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.FALSE);

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_1 = null;
				int limit_tFileInputDelimited_1 = -1;
				try {

					Object filename_tFileInputDelimited_1 = "C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/input/ratings.csv";
					if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_1 = 0, random_value_tFileInputDelimited_1 = -1;
						if (footer_value_tFileInputDelimited_1 > 0 || random_value_tFileInputDelimited_1 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_1 = new org.talend.fileprocess.FileInputDelimited(
								"C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/input/ratings.csv",
								"US-ASCII", ",", "\n", false, 1, 0, limit_tFileInputDelimited_1, -1, false);
					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					}

					while (fid_tFileInputDelimited_1 != null && fid_tFileInputDelimited_1.nextRecord()) {
						rowstate_tFileInputDelimited_1.reset();

						row3 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row3 = new row3Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_1 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_1 = 0;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row3.userId = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"userId", "row3", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row3.userId = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 1;

							temp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);
							if (temp.length() > 0) {

								try {

									row3.movieId = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_1) {
									globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
											ex_tFileInputDelimited_1.getMessage());
									rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"movieId", "row3", temp, ex_tFileInputDelimited_1),
											ex_tFileInputDelimited_1));
								}

							} else {

								row3.movieId = null;

							}

							columnIndexWithD_tFileInputDelimited_1 = 2;

							row3.rating = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							columnIndexWithD_tFileInputDelimited_1 = 3;

							row3.timestamp = fid_tFileInputDelimited_1.get(columnIndexWithD_tFileInputDelimited_1);

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							System.err.println(e.getMessage());
							row3 = null;

						}

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						if (row3 != null) {
							globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.TRUE);
							if (runTrace.isPause()) {
								while (runTrace.isPause()) {
									Thread.sleep(100);
								}
							} else {

								// here we dump the line content for trace purpose
								java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

								runTraceData.put("userId", String.valueOf(row3.userId));

								runTraceData.put("movieId", String.valueOf(row3.movieId));

								runTraceData.put("rating", String.valueOf(row3.rating));

								runTraceData.put("timestamp", String.valueOf(row3.timestamp));

								runTrace.sendTrace("row3", "tFileInputDelimited_1", runTraceData);
							}

						}

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */

						/**
						 * [tFileInputDelimited_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_begin ] stop
						 */
// Start of branch "row3"
						if (row3 != null) {

							/**
							 * [tMap_1 main ] start
							 */

							currentComponent = "tMap_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row3"

								);
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_1 = false;
							boolean mainRowRejected_tMap_1 = false;

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
								// ###############################
								// # Output tables

								out1 = null;

// # Output table : 'out1'
								out1_tmp.userId = row3.userId;
								out1_tmp.movieId = row3.movieId;
								out1_tmp.rating = Float
										.parseFloat(StringHandling.EREPLACE(row3.rating, "[^0-9.]", "")) > 5
												? 5
												: (Float.parseFloat(
														StringHandling.EREPLACE(row3.rating, "[^0-9.]", "")) < 0 ? 0
																: Float.parseFloat(StringHandling.EREPLACE(row3.rating,
																		"[^0-9.]", "")));
								out1_tmp.timestamp = new Date(Long.parseLong(row3.timestamp) * 1000);
								out1 = out1_tmp;
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_1 = false;

							if (out1 != null) {
								globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.TRUE);
								if (runTrace.isPause()) {
									while (runTrace.isPause()) {
										Thread.sleep(100);
									}
								} else {

									// here we dump the line content for trace purpose
									java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

									runTraceData.put("userId", String.valueOf(out1.userId));

									runTraceData.put("movieId", String.valueOf(out1.movieId));

									runTraceData.put("rating", String.valueOf(out1.rating));

									runTraceData.put("timestamp", String.valueOf(out1.timestamp));

									runTrace.sendTrace("out1", "tFileInputDelimited_1", runTraceData);
								}

							}

							tos_count_tMap_1++;

							/**
							 * [tMap_1 main ] stop
							 */

							/**
							 * [tMap_1 process_data_begin ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_begin ] stop
							 */
// Start of branch "out1"
							if (out1 != null) {

								/**
								 * [tMap_2 main ] start
								 */

								currentComponent = "tMap_2";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "out1"

									);
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_2 = false;

								// ###############################
								// # Input tables (lookups)
								boolean rejectedInnerJoin_tMap_2 = false;
								boolean mainRowRejected_tMap_2 = false;

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_2__Struct Var = Var__tMap_2;// ###############################
									// ###############################
									// # Output tables

									out2 = null;

// # Output table : 'out2'
									out2_tmp.timestamp = out1.timestamp;
									out2 = out2_tmp;
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_2 = false;

								if (out2 != null) {
									globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.TRUE);
									if (runTrace.isPause()) {
										while (runTrace.isPause()) {
											Thread.sleep(100);
										}
									} else {

										// here we dump the line content for trace purpose
										java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

										runTraceData.put("timestamp", String.valueOf(out2.timestamp));

										runTrace.sendTrace("out2", "tFileInputDelimited_1", runTraceData);
									}

								}

								tos_count_tMap_2++;

								/**
								 * [tMap_2 main ] stop
								 */

								/**
								 * [tMap_2 process_data_begin ] start
								 */

								currentComponent = "tMap_2";

								/**
								 * [tMap_2 process_data_begin ] stop
								 */
// Start of branch "out2"
								if (out2 != null) {

									/**
									 * [tMap_3 main ] start
									 */

									currentComponent = "tMap_3";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "out2"

										);
									}

									boolean hasCasePrimitiveKeyWithNull_tMap_3 = false;

									// ###############################
									// # Input tables (lookups)
									boolean rejectedInnerJoin_tMap_3 = false;
									boolean mainRowRejected_tMap_3 = false;

									// ###############################
									{ // start of Var scope

										// ###############################
										// # Vars tables

										Var__tMap_3__Struct Var = Var__tMap_3;// ###############################
										// ###############################
										// # Output tables

										out3 = null;

// # Output table : 'out3'
										out3_tmp.DateKey = Integer
												.parseInt(TalendDate.formatDate("yyyyMMdd", out2.timestamp));
										out3_tmp.mouth = Integer.parseInt(TalendDate.formatDate("MM", out2.timestamp));
										out3_tmp.year = TalendDate.getPartOfDate("YEAR", out2.timestamp);
										out3_tmp.day = Integer.parseInt(TalendDate.formatDate("dd", out2.timestamp));
										out3 = out3_tmp;
// ###############################

									} // end of Var scope

									rejectedInnerJoin_tMap_3 = false;

									if (out3 != null) {
										globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1", Boolean.TRUE);
										if (runTrace.isPause()) {
											while (runTrace.isPause()) {
												Thread.sleep(100);
											}
										} else {

											// here we dump the line content for trace purpose
											java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

											runTraceData.put("DateKey", String.valueOf(out3.DateKey));

											runTraceData.put("mouth", String.valueOf(out3.mouth));

											runTraceData.put("year", String.valueOf(out3.year));

											runTraceData.put("day", String.valueOf(out3.day));

											runTrace.sendTrace("out3", "tFileInputDelimited_1", runTraceData);
										}

									}

									tos_count_tMap_3++;

									/**
									 * [tMap_3 main ] stop
									 */

									/**
									 * [tMap_3 process_data_begin ] start
									 */

									currentComponent = "tMap_3";

									/**
									 * [tMap_3 process_data_begin ] stop
									 */
// Start of branch "out3"
									if (out3 != null) {

										/**
										 * [tUniqRow_1 main ] start
										 */

										currentComponent = "tUniqRow_1";

										if (execStat) {
											runStat.updateStatOnConnection(iterateId, 1, 1

													, "out3"

											);
										}

										finder_tUniqRow_1.DateKey = out3.DateKey;
										finder_tUniqRow_1.hashCodeDirty = true;
										if (!keystUniqRow_1.contains(finder_tUniqRow_1)) {
											KeyStruct_tUniqRow_1 new_tUniqRow_1 = new KeyStruct_tUniqRow_1();

											new_tUniqRow_1.DateKey = out3.DateKey;

											keystUniqRow_1.add(new_tUniqRow_1);
											nb_uniques_tUniqRow_1++;
										} else {
											nb_duplicates_tUniqRow_1++;
										}

										tos_count_tUniqRow_1++;

										/**
										 * [tUniqRow_1 main ] stop
										 */

										/**
										 * [tUniqRow_1 process_data_begin ] start
										 */

										currentComponent = "tUniqRow_1";

										/**
										 * [tUniqRow_1 process_data_begin ] stop
										 */

										/**
										 * [tUniqRow_1 process_data_end ] start
										 */

										currentComponent = "tUniqRow_1";

										/**
										 * [tUniqRow_1 process_data_end ] stop
										 */

									} // End of branch "out3"

									/**
									 * [tMap_3 process_data_end ] start
									 */

									currentComponent = "tMap_3";

									/**
									 * [tMap_3 process_data_end ] stop
									 */

								} // End of branch "out2"

								/**
								 * [tMap_2 process_data_end ] start
								 */

								currentComponent = "tMap_2";

								/**
								 * [tMap_2 process_data_end ] stop
								 */

							} // End of branch "out1"

							/**
							 * [tMap_1 process_data_end ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_end ] stop
							 */

						} // End of branch "row3"

						/**
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						if (!isChildJob && (Boolean) globalMap.get("ENABLE_TRACES_CONNECTION_tFileInputDelimited_1")) {
							if (globalMap.get("USE_CONDITION") != null && (Boolean) globalMap.get("USE_CONDITION")) {
								if (globalMap.get("TRACE_CONDITION") != null
										&& (Boolean) globalMap.get("TRACE_CONDITION")) {
									// if next breakpoint has been clicked on UI or if start job, should wait action
									// of user.
									if (runTrace.isNextBreakpoint()) {
										runTrace.waitForUserAction();
									} else if (runTrace.isNextRow()) {
										runTrace.waitForUserAction();
									}
								} else {
									// if next row has been clicked on UI or if start job, should wait action of
									// user.
									if (runTrace.isNextRow()) {
										runTrace.waitForUserAction();
									}
								}
							} else { // no condition set
								if (runTrace.isNextRow()) {
									runTrace.waitForUserAction();
								} else {
									Thread.sleep(1000);
								}
							}

						}
						globalMap.put("USE_CONDITION", Boolean.FALSE);

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

					}
				} finally {
					if (!((Object) ("C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/input/ratings.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_1 != null) {
							fid_tFileInputDelimited_1.close();
						}
					}
					if (fid_tFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", fid_tFileInputDelimited_1.getRowNumber());

					}
				}

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row3");
				}

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tMap_2 end ] start
				 */

				currentComponent = "tMap_2";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "out1");
				}

				ok_Hash.put("tMap_2", true);
				end_Hash.put("tMap_2", System.currentTimeMillis());

				/**
				 * [tMap_2 end ] stop
				 */

				/**
				 * [tMap_3 end ] start
				 */

				currentComponent = "tMap_3";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "out2");
				}

				ok_Hash.put("tMap_3", true);
				end_Hash.put("tMap_3", System.currentTimeMillis());

				/**
				 * [tMap_3 end ] stop
				 */

				/**
				 * [tUniqRow_1 end ] start
				 */

				currentComponent = "tUniqRow_1";

				globalMap.put("tUniqRow_1_NB_UNIQUES", nb_uniques_tUniqRow_1);
				globalMap.put("tUniqRow_1_NB_DUPLICATES", nb_duplicates_tUniqRow_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "out3");
				}

				ok_Hash.put("tUniqRow_1", true);
				end_Hash.put("tUniqRow_1", System.currentTimeMillis());

				/**
				 * [tUniqRow_1 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tMap_2 finally ] start
				 */

				currentComponent = "tMap_2";

				/**
				 * [tMap_2 finally ] stop
				 */

				/**
				 * [tMap_3 finally ] start
				 */

				currentComponent = "tMap_3";

				/**
				 * [tMap_3 finally ] stop
				 */

				/**
				 * [tUniqRow_1 finally ] start
				 */

				currentComponent = "tUniqRow_1";

				/**
				 * [tUniqRow_1 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
	}

	public static class row7Struct implements routines.system.IPersistableRow<row7Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public int movieId;

		public int getMovieId() {
			return this.movieId;
		}

		public String cleaned_title;

		public String getCleaned_title() {
			return this.cleaned_title;
		}

		public String year;

		public String getYear() {
			return this.year;
		}

		public String genre1;

		public String getGenre1() {
			return this.genre1;
		}

		public String genre2;

		public String getGenre2() {
			return this.genre2;
		}

		public String genre3;

		public String getGenre3() {
			return this.genre3;
		}

		public String genre4;

		public String getGenre4() {
			return this.genre4;
		}

		public String genre5;

		public String getGenre5() {
			return this.genre5;
		}

		public String genre6;

		public String getGenre6() {
			return this.genre6;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.cleaned_title = readString(dis);

					this.year = readString(dis);

					this.genre1 = readString(dis);

					this.genre2 = readString(dis);

					this.genre3 = readString(dis);

					this.genre4 = readString(dis);

					this.genre5 = readString(dis);

					this.genre6 = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.cleaned_title = readString(dis);

					this.year = readString(dis);

					this.genre1 = readString(dis);

					this.genre2 = readString(dis);

					this.genre3 = readString(dis);

					this.genre4 = readString(dis);

					this.genre5 = readString(dis);

					this.genre6 = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.cleaned_title, dos);

				// String

				writeString(this.year, dos);

				// String

				writeString(this.genre1, dos);

				// String

				writeString(this.genre2, dos);

				// String

				writeString(this.genre3, dos);

				// String

				writeString(this.genre4, dos);

				// String

				writeString(this.genre5, dos);

				// String

				writeString(this.genre6, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.cleaned_title, dos);

				// String

				writeString(this.year, dos);

				// String

				writeString(this.genre1, dos);

				// String

				writeString(this.genre2, dos);

				// String

				writeString(this.genre3, dos);

				// String

				writeString(this.genre4, dos);

				// String

				writeString(this.genre5, dos);

				// String

				writeString(this.genre6, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",cleaned_title=" + cleaned_title);
			sb.append(",year=" + year);
			sb.append(",genre1=" + genre1);
			sb.append(",genre2=" + genre2);
			sb.append(",genre3=" + genre3);
			sb.append(",genre4=" + genre4);
			sb.append(",genre5=" + genre5);
			sb.append(",genre6=" + genre6);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row7Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class split_genreStruct implements routines.system.IPersistableRow<split_genreStruct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public int movieId;

		public int getMovieId() {
			return this.movieId;
		}

		public String cleaned_title;

		public String getCleaned_title() {
			return this.cleaned_title;
		}

		public String year;

		public String getYear() {
			return this.year;
		}

		public String genre1;

		public String getGenre1() {
			return this.genre1;
		}

		public String genre2;

		public String getGenre2() {
			return this.genre2;
		}

		public String genre3;

		public String getGenre3() {
			return this.genre3;
		}

		public String genre4;

		public String getGenre4() {
			return this.genre4;
		}

		public String genre5;

		public String getGenre5() {
			return this.genre5;
		}

		public String genre6;

		public String getGenre6() {
			return this.genre6;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.cleaned_title = readString(dis);

					this.year = readString(dis);

					this.genre1 = readString(dis);

					this.genre2 = readString(dis);

					this.genre3 = readString(dis);

					this.genre4 = readString(dis);

					this.genre5 = readString(dis);

					this.genre6 = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.cleaned_title = readString(dis);

					this.year = readString(dis);

					this.genre1 = readString(dis);

					this.genre2 = readString(dis);

					this.genre3 = readString(dis);

					this.genre4 = readString(dis);

					this.genre5 = readString(dis);

					this.genre6 = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.cleaned_title, dos);

				// String

				writeString(this.year, dos);

				// String

				writeString(this.genre1, dos);

				// String

				writeString(this.genre2, dos);

				// String

				writeString(this.genre3, dos);

				// String

				writeString(this.genre4, dos);

				// String

				writeString(this.genre5, dos);

				// String

				writeString(this.genre6, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.cleaned_title, dos);

				// String

				writeString(this.year, dos);

				// String

				writeString(this.genre1, dos);

				// String

				writeString(this.genre2, dos);

				// String

				writeString(this.genre3, dos);

				// String

				writeString(this.genre4, dos);

				// String

				writeString(this.genre5, dos);

				// String

				writeString(this.genre6, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",cleaned_title=" + cleaned_title);
			sb.append(",year=" + year);
			sb.append(",genre1=" + genre1);
			sb.append(",genre2=" + genre2);
			sb.append(",genre3=" + genre3);
			sb.append(",genre4=" + genre4);
			sb.append(",genre5=" + genre5);
			sb.append(",genre6=" + genre6);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(split_genreStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class year_validStruct implements routines.system.IPersistableRow<year_validStruct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public int movieId;

		public int getMovieId() {
			return this.movieId;
		}

		public String cleaned_title;

		public String getCleaned_title() {
			return this.cleaned_title;
		}

		public String year;

		public String getYear() {
			return this.year;
		}

		public String genres;

		public String getGenres() {
			return this.genres;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.cleaned_title = readString(dis);

					this.year = readString(dis);

					this.genres = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.cleaned_title = readString(dis);

					this.year = readString(dis);

					this.genres = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.cleaned_title, dos);

				// String

				writeString(this.year, dos);

				// String

				writeString(this.genres, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.cleaned_title, dos);

				// String

				writeString(this.year, dos);

				// String

				writeString(this.genres, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",cleaned_title=" + cleaned_title);
			sb.append(",year=" + year);
			sb.append(",genres=" + genres);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(year_validStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row6Struct implements routines.system.IPersistableRow<row6Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public int movieId;

		public int getMovieId() {
			return this.movieId;
		}

		public String title;

		public String getTitle() {
			return this.title;
		}

		public String genres;

		public String getGenres() {
			return this.genres;
		}

		public String errorCode;

		public String getErrorCode() {
			return this.errorCode;
		}

		public String errorMessage;

		public String getErrorMessage() {
			return this.errorMessage;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.title = readString(dis);

					this.genres = readString(dis);

					this.errorCode = readString(dis);

					this.errorMessage = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.title = readString(dis);

					this.genres = readString(dis);

					this.errorCode = readString(dis);

					this.errorMessage = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

				// String

				writeString(this.errorCode, dos);

				// String

				writeString(this.errorMessage, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

				// String

				writeString(this.errorCode, dos);

				// String

				writeString(this.errorMessage, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",title=" + title);
			sb.append(",genres=" + genres);
			sb.append(",errorCode=" + errorCode);
			sb.append(",errorMessage=" + errorMessage);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row6Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public int movieId;

		public int getMovieId() {
			return this.movieId;
		}

		public String title;

		public String getTitle() {
			return this.title;
		}

		public String genres;

		public String getGenres() {
			return this.genres;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.title = readString(dis);

					this.genres = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.title = readString(dis);

					this.genres = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",title=" + title);
			sb.append(",genres=" + genres);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public int movieId;

		public int getMovieId() {
			return this.movieId;
		}

		public String title;

		public String getTitle() {
			return this.title;
		}

		public String genres;

		public String getGenres() {
			return this.genres;
		}

		public String errorCode;

		public String getErrorCode() {
			return this.errorCode;
		}

		public String errorMessage;

		public String getErrorMessage() {
			return this.errorMessage;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.title = readString(dis);

					this.genres = readString(dis);

					this.errorCode = readString(dis);

					this.errorMessage = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = dis.readInt();

					this.title = readString(dis);

					this.genres = readString(dis);

					this.errorCode = readString(dis);

					this.errorMessage = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

				// String

				writeString(this.errorCode, dos);

				// String

				writeString(this.errorMessage, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.movieId);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

				// String

				writeString(this.errorCode, dos);

				// String

				writeString(this.errorMessage, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",title=" + title);
			sb.append(",genres=" + genres);
			sb.append(",errorCode=" + errorCode);
			sb.append(",errorMessage=" + errorMessage);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public String title;

		public String getTitle() {
			return this.title;
		}

		public String genres;

		public String getGenres() {
			return this.genres;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = readInteger(dis);

					this.title = readString(dis);

					this.genres = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = readInteger(dis);

					this.title = readString(dis);

					this.genres = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.movieId, dos);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.movieId, dos);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",title=" + title);
			sb.append(",genres=" + genres);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public String title;

		public String getTitle() {
			return this.title;
		}

		public String genres;

		public String getGenres() {
			return this.genres;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = readInteger(dis);

					this.title = readString(dis);

					this.genres = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = readInteger(dis);

					this.title = readString(dis);

					this.genres = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.movieId, dos);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.movieId, dos);

				// String

				writeString(this.title, dos);

				// String

				writeString(this.genres, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",title=" + title);
			sb.append(",genres=" + genres);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row1Struct row1 = new row1Struct();
				row1Struct row2 = row1;
				row4Struct row4 = new row4Struct();
				year_validStruct year_valid = new year_validStruct();
				split_genreStruct split_genre = new split_genreStruct();
				split_genreStruct row7 = split_genre;
				row5Struct row5 = new row5Struct();
				row5Struct row6 = row5;

				/**
				 * [tDBOutput_1 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row7");
				}

				int tos_count_tDBOutput_1 = 0;

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;
				String dbschema_tDBOutput_1 = null;
				String tableName_tDBOutput_1 = null;
				boolean whetherReject_tDBOutput_1 = false;

				java.util.Calendar calendar_tDBOutput_1 = java.util.Calendar.getInstance();
				long year1_tDBOutput_1 = TalendDate.parseDate("yyyy-MM-dd", "0001-01-01").getTime();
				long year2_tDBOutput_1 = TalendDate.parseDate("yyyy-MM-dd", "1753-01-01").getTime();
				long year10000_tDBOutput_1 = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", "9999-12-31 24:00:00")
						.getTime();
				long date_tDBOutput_1;

				java.util.Calendar calendar_datetimeoffset_tDBOutput_1 = java.util.Calendar
						.getInstance(java.util.TimeZone.getTimeZone("UTC"));

				java.sql.Connection conn_tDBOutput_1 = null;
				String dbUser_tDBOutput_1 = null;
				dbschema_tDBOutput_1 = "dbo";
				String driverClass_tDBOutput_1 = "net.sourceforge.jtds.jdbc.Driver";

				java.lang.Class.forName(driverClass_tDBOutput_1);
				String port_tDBOutput_1 = "1433";
				String dbname_tDBOutput_1 = "rabii";
				String url_tDBOutput_1 = "jdbc:jtds:sqlserver://" + "localhost";
				if (!"".equals(port_tDBOutput_1)) {
					url_tDBOutput_1 += ":" + "1433";
				}
				if (!"".equals(dbname_tDBOutput_1)) {
					url_tDBOutput_1 += "//" + "rabii";

				}
				url_tDBOutput_1 += ";appName=" + projectName + ";" + "instance=SQLEXPRESS";
				dbUser_tDBOutput_1 = "sa";

				final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:0R0rm7seHLZG2a4Jqfh+AWO2izAJ0OTYNfdRLRGKgkEQR3Q=");

				String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;
				conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1, dbUser_tDBOutput_1,
						dbPwd_tDBOutput_1);

				resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);

				conn_tDBOutput_1.setAutoCommit(false);
				int commitEvery_tDBOutput_1 = 10000;
				int commitCounter_tDBOutput_1 = 0;

				int batchSize_tDBOutput_1 = 10000;
				int batchSizeCounter_tDBOutput_1 = 0;

				if (dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
					tableName_tDBOutput_1 = "movies";
				} else {
					tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "].[" + "movies";
				}
				int count_tDBOutput_1 = 0;

				boolean whetherExist_tDBOutput_1 = false;
				try (java.sql.Statement isExistStmt_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
					try {
						isExistStmt_tDBOutput_1.execute("SELECT TOP 1 1 FROM [" + tableName_tDBOutput_1 + "]");
						whetherExist_tDBOutput_1 = true;
					} catch (java.lang.Exception e) {
						globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());
						whetherExist_tDBOutput_1 = false;
					}
				}
				if (!whetherExist_tDBOutput_1) {
					try (java.sql.Statement stmtCreate_tDBOutput_1 = conn_tDBOutput_1.createStatement()) {
						stmtCreate_tDBOutput_1.execute("CREATE TABLE [" + tableName_tDBOutput_1
								+ "]([movieId] INT  not null ,[cleaned_title] CHAR(100)  ,[year] CHAR(100)  ,[genre1] CHAR(100)  ,[genre2] CHAR(100)  ,[genre3] CHAR(100)  ,[genre4] CHAR(100)  ,[genre5] CHAR(100)  ,[genre6] CHAR(100)  ,primary key([movieId]))");
					}
				}
				String insert_tDBOutput_1 = "INSERT INTO [" + tableName_tDBOutput_1
						+ "] ([movieId],[cleaned_title],[year],[genre1],[genre2],[genre3],[genre4],[genre5],[genre6]) VALUES (?,?,?,?,?,?,?,?,?)";
				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tFlowMeter_5 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tFlowMeter_5", false);
				start_Hash.put("tFlowMeter_5", System.currentTimeMillis());

				currentComponent = "tFlowMeter_5";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "split_genre");
				}

				int tos_count_tFlowMeter_5 = 0;

				int count_tFlowMeter_5 = 0;

				/**
				 * [tFlowMeter_5 begin ] stop
				 */

				/**
				 * [tMap_5 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tMap_5", false);
				start_Hash.put("tMap_5", System.currentTimeMillis());

				currentComponent = "tMap_5";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "year_valid");
				}

				int tos_count_tMap_5 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_5__Struct {
				}
				Var__tMap_5__Struct Var__tMap_5 = new Var__tMap_5__Struct();
// ###############################

// ###############################
// # Outputs initialization
				split_genreStruct split_genre_tmp = new split_genreStruct();
// ###############################

				/**
				 * [tMap_5 begin ] stop
				 */

				/**
				 * [tMap_4 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tMap_4", false);
				start_Hash.put("tMap_4", System.currentTimeMillis());

				currentComponent = "tMap_4";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row4");
				}

				int tos_count_tMap_4 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_4__Struct {
				}
				Var__tMap_4__Struct Var__tMap_4 = new Var__tMap_4__Struct();
// ###############################

// ###############################
// # Outputs initialization
				year_validStruct year_valid_tmp = new year_validStruct();
// ###############################

				/**
				 * [tMap_4 begin ] stop
				 */

				/**
				 * [tLogRow_3 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tLogRow_3", false);
				start_Hash.put("tLogRow_3", System.currentTimeMillis());

				currentComponent = "tLogRow_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row6");
				}

				int tos_count_tLogRow_3 = 0;

				///////////////////////

				final String OUTPUT_FIELD_SEPARATOR_tLogRow_3 = "|";
				java.io.PrintStream consoleOut_tLogRow_3 = null;

				StringBuilder strBuffer_tLogRow_3 = null;
				int nb_line_tLogRow_3 = 0;
///////////////////////    			

				/**
				 * [tLogRow_3 begin ] stop
				 */

				/**
				 * [tLogRow_2 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tLogRow_2", false);
				start_Hash.put("tLogRow_2", System.currentTimeMillis());

				currentComponent = "tLogRow_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row5");
				}

				int tos_count_tLogRow_2 = 0;

				///////////////////////

				class Util_tLogRow_2 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[5];

					public void addRow(String[] row) {

						for (int i = 0; i < 5; i++) {
							if (row[i] != null) {
								colLengths[i] = Math.max(colLengths[i], row[i].length());
							}
						}
						list.add(row);
					}

					public void setTableName(String name) {

						this.name = name;
					}

					public StringBuilder format() {

						StringBuilder sb = new StringBuilder();

						sb.append(print(des_top));

						int totals = 0;
						for (int i = 0; i < colLengths.length; i++) {
							totals = totals + colLengths[i];
						}

						// name
						sb.append("|");
						int k = 0;
						for (k = 0; k < (totals + 4 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 4 - name.length() - k; i++) {
							sb.append(' ');
						}
						sb.append("|\n");

						// head and rows
						sb.append(print(des_head));
						for (int i = 0; i < list.size(); i++) {

							String[] row = list.get(i);

							java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());

							StringBuilder sbformat = new StringBuilder();
							sbformat.append("|%1$-");
							sbformat.append(colLengths[0]);
							sbformat.append("s");

							sbformat.append("|%2$-");
							sbformat.append(colLengths[1]);
							sbformat.append("s");

							sbformat.append("|%3$-");
							sbformat.append(colLengths[2]);
							sbformat.append("s");

							sbformat.append("|%4$-");
							sbformat.append(colLengths[3]);
							sbformat.append("s");

							sbformat.append("|%5$-");
							sbformat.append(colLengths[4]);
							sbformat.append("s");

							sbformat.append("|\n");

							formatter.format(sbformat.toString(), (Object[]) row);

							sb.append(formatter.toString());
							if (i == 0)
								sb.append(print(des_head)); // print the head
						}

						// end
						sb.append(print(des_bottom));
						return sb;
					}

					private StringBuilder print(String[] fillChars) {
						StringBuilder sb = new StringBuilder();
						// first column
						sb.append(fillChars[0]);
						for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						for (int i = 0; i < colLengths[1] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[2] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);
						for (int i = 0; i < colLengths[3] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						// last column
						for (int i = 0; i < colLengths[4] - fillChars[1].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[1]);
						sb.append("\n");
						return sb;
					}

					public boolean isTableEmpty() {
						if (list.size() > 1)
							return false;
						return true;
					}
				}
				Util_tLogRow_2 util_tLogRow_2 = new Util_tLogRow_2();
				util_tLogRow_2.setTableName("null_data");
				util_tLogRow_2.addRow(new String[] { "movieId", "title", "genres", "errorCode", "errorMessage", });
				StringBuilder strBuffer_tLogRow_2 = null;
				int nb_line_tLogRow_2 = 0;
///////////////////////    			

				/**
				 * [tLogRow_2 begin ] stop
				 */

				/**
				 * [tSchemaComplianceCheck_1 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tSchemaComplianceCheck_1", false);
				start_Hash.put("tSchemaComplianceCheck_1", System.currentTimeMillis());

				currentComponent = "tSchemaComplianceCheck_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
				}

				int tos_count_tSchemaComplianceCheck_1 = 0;

				class RowSetValueUtil_tSchemaComplianceCheck_1 {

					boolean ifPassedThrough = true;
					int errorCodeThrough = 0;
					String errorMessageThrough = "";
					int resultErrorCodeThrough = 0;
					String resultErrorMessageThrough = "";
					String tmpContentThrough = null;

					boolean ifPassed = true;
					int errorCode = 0;
					String errorMessage = "";

					void handleBigdecimalPrecision(String data, int iPrecision, int maxLength) {
						// number of digits before the decimal point(ignoring frontend zeroes)
						int len1 = 0;
						int len2 = 0;
						ifPassed = true;
						errorCode = 0;
						errorMessage = "";
						if (data.startsWith("-")) {
							data = data.substring(1);
						}
						data = org.apache.commons.lang.StringUtils.stripStart(data, "0");

						if (data.indexOf(".") >= 0) {
							len1 = data.indexOf(".");
							data = org.apache.commons.lang.StringUtils.stripEnd(data, "0");
							len2 = data.length() - (len1 + 1);
						} else {
							len1 = data.length();
						}

						if (iPrecision < len2) {
							ifPassed = false;
							errorCode += 8;
							errorMessage += "|precision Non-matches";
						} else if (maxLength < len1 + iPrecision) {
							ifPassed = false;
							errorCode += 8;
							errorMessage += "|invalid Length setting is unsuitable for Precision";
						}
					}

					int handleErrorCode(int errorCode, int resultErrorCode) {
						if (errorCode > 0) {
							if (resultErrorCode > 0) {
								resultErrorCode = 16;
							} else {
								resultErrorCode = errorCode;
							}
						}
						return resultErrorCode;
					}

					String handleErrorMessage(String errorMessage, String resultErrorMessage, String columnLabel) {
						if (errorMessage.length() > 0) {
							if (resultErrorMessage.length() > 0) {
								resultErrorMessage += ";" + errorMessage.replaceFirst("\\|", columnLabel);
							} else {
								resultErrorMessage = errorMessage.replaceFirst("\\|", columnLabel);
							}
						}
						return resultErrorMessage;
					}

					void reset() {
						ifPassedThrough = true;
						errorCodeThrough = 0;
						errorMessageThrough = "";
						resultErrorCodeThrough = 0;
						resultErrorMessageThrough = "";
						tmpContentThrough = null;

						ifPassed = true;
						errorCode = 0;
						errorMessage = "";
					}

					void setRowValue_0(row1Struct row2) {
						// validate nullable (empty as null)
						if ((row2.movieId == null) || ("".equals(row2.movieId))) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						if (row2.movieId != null) {
							tmpContentThrough = row2.movieId.toString();

							if (tmpContentThrough.length() > 200) {
								ifPassedThrough = false;
								errorCodeThrough += 8;
								errorMessageThrough += "|exceed max length";
							}
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"movieId:");
						errorMessageThrough = "";
						// validate nullable (empty as null)
						if ((row2.title == null) || ("".equals(row2.title))) {
							ifPassedThrough = false;
							errorCodeThrough += 4;
							errorMessageThrough += "|empty or null";
						}
						try {
							if (row2.title != null) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row2.title);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						if (row2.title != null) {
							if (row2.title.length() > 520) {
								ifPassedThrough = false;
								errorCodeThrough += 8;
								errorMessageThrough += "|exceed max length";
							}
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"title:");
						errorMessageThrough = "";
						try {
							if (row2.genres != null && (!"".equals(row2.genres))) {
								String tester_tSchemaComplianceCheck_1 = String.valueOf(row2.genres);
							}
						} catch (java.lang.Exception e) {
							globalMap.put("tSchemaComplianceCheck_1_ERROR_MESSAGE", e.getMessage());
							ifPassedThrough = false;
							errorCodeThrough += 2;
							errorMessageThrough += "|wrong type";
						}
						if (row2.genres != null && (!"".equals(row2.genres))) {
							if (row2.genres.length() > 430) {
								ifPassedThrough = false;
								errorCodeThrough += 8;
								errorMessageThrough += "|exceed max length";
							}
						}
						resultErrorCodeThrough = handleErrorCode(errorCodeThrough, resultErrorCodeThrough);
						errorCodeThrough = 0;
						resultErrorMessageThrough = handleErrorMessage(errorMessageThrough, resultErrorMessageThrough,
								"genres:");
						errorMessageThrough = "";
					}
				}
				RowSetValueUtil_tSchemaComplianceCheck_1 rsvUtil_tSchemaComplianceCheck_1 = new RowSetValueUtil_tSchemaComplianceCheck_1();

				/**
				 * [tSchemaComplianceCheck_1 begin ] stop
				 */

				/**
				 * [tLogRow_1 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tLogRow_1", false);
				start_Hash.put("tLogRow_1", System.currentTimeMillis());

				currentComponent = "tLogRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
				}

				int tos_count_tLogRow_1 = 0;

				///////////////////////

				class Util_tLogRow_1 {

					String[] des_top = { ".", ".", "-", "+" };

					String[] des_head = { "|=", "=|", "-", "+" };

					String[] des_bottom = { "'", "'", "-", "+" };

					String name = "";

					java.util.List<String[]> list = new java.util.ArrayList<String[]>();

					int[] colLengths = new int[3];

					public void addRow(String[] row) {

						for (int i = 0; i < 3; i++) {
							if (row[i] != null) {
								colLengths[i] = Math.max(colLengths[i], row[i].length());
							}
						}
						list.add(row);
					}

					public void setTableName(String name) {

						this.name = name;
					}

					public StringBuilder format() {

						StringBuilder sb = new StringBuilder();

						sb.append(print(des_top));

						int totals = 0;
						for (int i = 0; i < colLengths.length; i++) {
							totals = totals + colLengths[i];
						}

						// name
						sb.append("|");
						int k = 0;
						for (k = 0; k < (totals + 2 - name.length()) / 2; k++) {
							sb.append(' ');
						}
						sb.append(name);
						for (int i = 0; i < totals + 2 - name.length() - k; i++) {
							sb.append(' ');
						}
						sb.append("|\n");

						// head and rows
						sb.append(print(des_head));
						for (int i = 0; i < list.size(); i++) {

							String[] row = list.get(i);

							java.util.Formatter formatter = new java.util.Formatter(new StringBuilder());

							StringBuilder sbformat = new StringBuilder();
							sbformat.append("|%1$-");
							sbformat.append(colLengths[0]);
							sbformat.append("s");

							sbformat.append("|%2$-");
							sbformat.append(colLengths[1]);
							sbformat.append("s");

							sbformat.append("|%3$-");
							sbformat.append(colLengths[2]);
							sbformat.append("s");

							sbformat.append("|\n");

							formatter.format(sbformat.toString(), (Object[]) row);

							sb.append(formatter.toString());
							if (i == 0)
								sb.append(print(des_head)); // print the head
						}

						// end
						sb.append(print(des_bottom));
						return sb;
					}

					private StringBuilder print(String[] fillChars) {
						StringBuilder sb = new StringBuilder();
						// first column
						sb.append(fillChars[0]);
						for (int i = 0; i < colLengths[0] - fillChars[0].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						for (int i = 0; i < colLengths[1] - fillChars[3].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[3]);

						// last column
						for (int i = 0; i < colLengths[2] - fillChars[1].length() + 1; i++) {
							sb.append(fillChars[2]);
						}
						sb.append(fillChars[1]);
						sb.append("\n");
						return sb;
					}

					public boolean isTableEmpty() {
						if (list.size() > 1)
							return false;
						return true;
					}
				}
				Util_tLogRow_1 util_tLogRow_1 = new Util_tLogRow_1();
				util_tLogRow_1.setTableName("tLogRow_1");
				util_tLogRow_1.addRow(new String[] { "movieId", "title", "genres", });
				StringBuilder strBuffer_tLogRow_1 = null;
				int nb_line_tLogRow_1 = 0;
///////////////////////    			

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_2 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.FALSE);

				ok_Hash.put("tFileInputDelimited_2", false);
				start_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_2";

				int tos_count_tFileInputDelimited_2 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_2 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_2 = 0;
				int footer_tFileInputDelimited_2 = 0;
				int totalLinetFileInputDelimited_2 = 0;
				int limittFileInputDelimited_2 = -1;
				int lastLinetFileInputDelimited_2 = -1;

				char fieldSeparator_tFileInputDelimited_2[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ",").length() > 0) {
					fieldSeparator_tFileInputDelimited_2 = ((String) ",").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_2[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_2 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_2 = /** Start field tFileInputDelimited_2:FILENAME */
						context.input_file + "movies.csv"/** End field tFileInputDelimited_2:FILENAME */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_2 = null;

				try {

					String[] rowtFileInputDelimited_2 = null;
					int currentLinetFileInputDelimited_2 = 0;
					int outputLinetFileInputDelimited_2 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_2 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_2 = 0;
							if (footer_value_tFileInputDelimited_2 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_2,
									fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
						} else {
							csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_2),
									fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
						}

						csvReadertFileInputDelimited_2.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_2[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_2[0] != '\r'))
							csvReadertFileInputDelimited_2.setLineEnd("" + rowSeparator_tFileInputDelimited_2[0]);

						csvReadertFileInputDelimited_2.setQuoteChar('"');

						csvReadertFileInputDelimited_2.setEscapeChar(csvReadertFileInputDelimited_2.getQuoteChar());

						if (footer_tFileInputDelimited_2 > 0) {
							for (totalLinetFileInputDelimited_2 = 0; totalLinetFileInputDelimited_2 < 1; totalLinetFileInputDelimited_2++) {
								csvReadertFileInputDelimited_2.readNext();
							}
							csvReadertFileInputDelimited_2.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_2.readNext()) {

								totalLinetFileInputDelimited_2++;

							}
							int lastLineTemptFileInputDelimited_2 = totalLinetFileInputDelimited_2
									- footer_tFileInputDelimited_2 < 0 ? 0
											: totalLinetFileInputDelimited_2 - footer_tFileInputDelimited_2;
							if (lastLinetFileInputDelimited_2 > 0) {
								lastLinetFileInputDelimited_2 = lastLinetFileInputDelimited_2 < lastLineTemptFileInputDelimited_2
										? lastLinetFileInputDelimited_2
										: lastLineTemptFileInputDelimited_2;
							} else {
								lastLinetFileInputDelimited_2 = lastLineTemptFileInputDelimited_2;
							}

							csvReadertFileInputDelimited_2.close();
							if (filename_tFileInputDelimited_2 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_2,
										fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
							} else {
								csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_2),
										fieldSeparator_tFileInputDelimited_2[0], "UTF-8");
							}
							csvReadertFileInputDelimited_2.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_2[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_2[0] != '\r'))
								csvReadertFileInputDelimited_2.setLineEnd("" + rowSeparator_tFileInputDelimited_2[0]);

							csvReadertFileInputDelimited_2.setQuoteChar('"');

							csvReadertFileInputDelimited_2.setEscapeChar(csvReadertFileInputDelimited_2.getQuoteChar());

						}

						if (limittFileInputDelimited_2 != 0) {
							for (currentLinetFileInputDelimited_2 = 0; currentLinetFileInputDelimited_2 < 1; currentLinetFileInputDelimited_2++) {
								csvReadertFileInputDelimited_2.readNext();
							}
						}
						csvReadertFileInputDelimited_2.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_2 != 0 && csvReadertFileInputDelimited_2 != null
							&& csvReadertFileInputDelimited_2.readNext()) {
						rowstate_tFileInputDelimited_2.reset();

						rowtFileInputDelimited_2 = csvReadertFileInputDelimited_2.getValues();

						currentLinetFileInputDelimited_2++;

						if (lastLinetFileInputDelimited_2 > -1
								&& currentLinetFileInputDelimited_2 > lastLinetFileInputDelimited_2) {
							break;
						}
						outputLinetFileInputDelimited_2++;
						if (limittFileInputDelimited_2 > 0
								&& outputLinetFileInputDelimited_2 > limittFileInputDelimited_2) {
							break;
						}

						row1 = null;

						boolean whetherReject_tFileInputDelimited_2 = false;
						row1 = new row1Struct();
						try {

							char fieldSeparator_tFileInputDelimited_2_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ",").length() > 0) {
								fieldSeparator_tFileInputDelimited_2_ListType = ((String) ",").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_2.length == 1 && ("\015").equals(rowtFileInputDelimited_2[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row1.movieId = null;

								row1.title = null;

								row1.genres = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_2 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_2 = 0;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row1.movieId = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"movieId", "row1",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row1.movieId = null;

									}

								} else {

									row1.movieId = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 1;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row1.title = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row1.title = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 2;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row1.genres = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row1.genres = null;

								}

							}

							if (rowstate_tFileInputDelimited_2.getException() != null) {
								throw rowstate_tFileInputDelimited_2.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_2 = true;

							System.err.println(e.getMessage());
							row1 = null;

							globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_2 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_2 main ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						if (row1 != null) {
							globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.TRUE);
							if (runTrace.isPause()) {
								while (runTrace.isPause()) {
									Thread.sleep(100);
								}
							} else {

								// here we dump the line content for trace purpose
								java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

								runTraceData.put("movieId", String.valueOf(row1.movieId));

								runTraceData.put("title", String.valueOf(row1.title));

								runTraceData.put("genres", String.valueOf(row1.genres));

								runTrace.sendTrace("row1", "tFileInputDelimited_2", runTraceData);
							}

						}

						tos_count_tFileInputDelimited_2++;

						/**
						 * [tFileInputDelimited_2 main ] stop
						 */

						/**
						 * [tFileInputDelimited_2 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						/**
						 * [tFileInputDelimited_2 process_data_begin ] stop
						 */
// Start of branch "row1"
						if (row1 != null) {
							row5 = null;

							/**
							 * [tLogRow_1 main ] start
							 */

							currentComponent = "tLogRow_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row1"

								);
							}

///////////////////////		

							String[] row_tLogRow_1 = new String[3];

							if (row1.movieId != null) { //
								row_tLogRow_1[0] = String.valueOf(row1.movieId);

							} //

							if (row1.title != null) { //
								row_tLogRow_1[1] = String.valueOf(row1.title);

							} //

							if (row1.genres != null) { //
								row_tLogRow_1[2] = String.valueOf(row1.genres);

							} //

							util_tLogRow_1.addRow(row_tLogRow_1);
							nb_line_tLogRow_1++;
//////

//////                    

///////////////////////    			

							row2 = row1;

							if (row2 != null) {
								globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.TRUE);
								if (runTrace.isPause()) {
									while (runTrace.isPause()) {
										Thread.sleep(100);
									}
								} else {

									// here we dump the line content for trace purpose
									java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

									runTraceData.put("movieId", String.valueOf(row2.movieId));

									runTraceData.put("title", String.valueOf(row2.title));

									runTraceData.put("genres", String.valueOf(row2.genres));

									runTrace.sendTrace("row2", "tFileInputDelimited_2", runTraceData);
								}

							}

							tos_count_tLogRow_1++;

							/**
							 * [tLogRow_1 main ] stop
							 */

							/**
							 * [tLogRow_1 process_data_begin ] start
							 */

							currentComponent = "tLogRow_1";

							/**
							 * [tLogRow_1 process_data_begin ] stop
							 */

							/**
							 * [tSchemaComplianceCheck_1 main ] start
							 */

							currentComponent = "tSchemaComplianceCheck_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row2"

								);
							}

							row4 = null;
							row5 = null;
							rsvUtil_tSchemaComplianceCheck_1.setRowValue_0(row2);
							if (rsvUtil_tSchemaComplianceCheck_1.ifPassedThrough) {
								row4 = new row4Struct();
								row4.movieId = row2.movieId;
								row4.title = row2.title;
								row4.genres = row2.genres;
							}
							if (!rsvUtil_tSchemaComplianceCheck_1.ifPassedThrough) {
								row5 = new row5Struct();
								row5.movieId = row2.movieId;
								row5.title = row2.title;
								row5.genres = row2.genres;
								row5.errorCode = String
										.valueOf(rsvUtil_tSchemaComplianceCheck_1.resultErrorCodeThrough);
								row5.errorMessage = rsvUtil_tSchemaComplianceCheck_1.resultErrorMessageThrough;
							}
							rsvUtil_tSchemaComplianceCheck_1.reset();

							if (row4 != null) {
								globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.TRUE);
								if (runTrace.isPause()) {
									while (runTrace.isPause()) {
										Thread.sleep(100);
									}
								} else {

									// here we dump the line content for trace purpose
									java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

									runTraceData.put("movieId", String.valueOf(row4.movieId));

									runTraceData.put("title", String.valueOf(row4.title));

									runTraceData.put("genres", String.valueOf(row4.genres));

									runTrace.sendTrace("row4", "tFileInputDelimited_2", runTraceData);
								}

							}

							if (row5 != null) {
								globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.TRUE);
								if (runTrace.isPause()) {
									while (runTrace.isPause()) {
										Thread.sleep(100);
									}
								} else {

									// here we dump the line content for trace purpose
									java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

									runTraceData.put("movieId", String.valueOf(row5.movieId));

									runTraceData.put("title", String.valueOf(row5.title));

									runTraceData.put("genres", String.valueOf(row5.genres));

									runTraceData.put("errorCode", String.valueOf(row5.errorCode));

									runTraceData.put("errorMessage", String.valueOf(row5.errorMessage));

									runTrace.sendTrace("row5", "tFileInputDelimited_2", runTraceData);
								}

							}

							tos_count_tSchemaComplianceCheck_1++;

							/**
							 * [tSchemaComplianceCheck_1 main ] stop
							 */

							/**
							 * [tSchemaComplianceCheck_1 process_data_begin ] start
							 */

							currentComponent = "tSchemaComplianceCheck_1";

							/**
							 * [tSchemaComplianceCheck_1 process_data_begin ] stop
							 */
// Start of branch "row4"
							if (row4 != null) {

								/**
								 * [tMap_4 main ] start
								 */

								currentComponent = "tMap_4";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row4"

									);
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_4 = false;

								// ###############################
								// # Input tables (lookups)
								boolean rejectedInnerJoin_tMap_4 = false;
								boolean mainRowRejected_tMap_4 = false;

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_4__Struct Var = Var__tMap_4;// ###############################
									// ###############################
									// # Output tables

									year_valid = null;

// # Output table : 'year_valid'
									year_valid_tmp.movieId = row7.movieId;
									year_valid_tmp.cleaned_title = (row5.title != null
											&& row5.title.matches(".*\\(\\d{4}\\).*"))
													? row5.title.replaceAll("\\(\\d{4}\\)", "")
													: row5.title;
									year_valid_tmp.year = (row5.title != null && row5.title.matches(".*\\(\\d{4}\\).*"))
											? row5.title.replaceAll(".*\\((\\d{4})\\).*", "$1")
											: null;
									;
									year_valid_tmp.genres = row5.genres;
									year_valid = year_valid_tmp;
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_4 = false;

								if (year_valid != null) {
									globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.TRUE);
									if (runTrace.isPause()) {
										while (runTrace.isPause()) {
											Thread.sleep(100);
										}
									} else {

										// here we dump the line content for trace purpose
										java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

										runTraceData.put("movieId", String.valueOf(year_valid.movieId));

										runTraceData.put("cleaned_title", String.valueOf(year_valid.cleaned_title));

										runTraceData.put("year", String.valueOf(year_valid.year));

										runTraceData.put("genres", String.valueOf(year_valid.genres));

										runTrace.sendTrace("year_valid", "tFileInputDelimited_2", runTraceData);
									}

								}

								tos_count_tMap_4++;

								/**
								 * [tMap_4 main ] stop
								 */

								/**
								 * [tMap_4 process_data_begin ] start
								 */

								currentComponent = "tMap_4";

								/**
								 * [tMap_4 process_data_begin ] stop
								 */
// Start of branch "year_valid"
								if (year_valid != null) {

									/**
									 * [tMap_5 main ] start
									 */

									currentComponent = "tMap_5";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "year_valid"

										);
									}

									boolean hasCasePrimitiveKeyWithNull_tMap_5 = false;

									// ###############################
									// # Input tables (lookups)
									boolean rejectedInnerJoin_tMap_5 = false;
									boolean mainRowRejected_tMap_5 = false;

									// ###############################
									{ // start of Var scope

										// ###############################
										// # Vars tables

										Var__tMap_5__Struct Var = Var__tMap_5;// ###############################
										// ###############################
										// # Output tables

										split_genre = null;

// # Output table : 'split_genre'
										split_genre_tmp.movieId = year_valid.movieId;
										split_genre_tmp.cleaned_title = year_valid.cleaned_title;
										split_genre_tmp.year = year_valid.year;
										split_genre_tmp.genre1 = (year_valid.genres != null
												&& year_valid.genres.split("\\|").length > 0)
														? year_valid.genres.split("\\|")[0]
														: "";
										split_genre_tmp.genre2 = (year_valid.genres != null
												&& year_valid.genres.split("\\|").length > 1)
														? year_valid.genres.split("\\|")[1]
														: "";
										split_genre_tmp.genre3 = (year_valid.genres != null
												&& year_valid.genres.split("\\|").length > 2)
														? year_valid.genres.split("\\|")[2]
														: "";
										split_genre_tmp.genre4 = (year_valid.genres != null
												&& year_valid.genres.split("\\|").length > 3)
														? year_valid.genres.split("\\|")[3]
														: "";
										split_genre_tmp.genre5 = (year_valid.genres != null
												&& year_valid.genres.split("\\|").length > 4)
														? year_valid.genres.split("\\|")[4]
														: "";
										split_genre_tmp.genre6 = (year_valid.genres != null
												&& year_valid.genres.split("\\|").length > 5)
														? year_valid.genres.split("\\|")[5]
														: "";
										split_genre = split_genre_tmp;
// ###############################

									} // end of Var scope

									rejectedInnerJoin_tMap_5 = false;

									if (split_genre != null) {
										globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.TRUE);
										if (runTrace.isPause()) {
											while (runTrace.isPause()) {
												Thread.sleep(100);
											}
										} else {

											// here we dump the line content for trace purpose
											java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

											runTraceData.put("movieId", String.valueOf(split_genre.movieId));

											runTraceData.put("cleaned_title",
													String.valueOf(split_genre.cleaned_title));

											runTraceData.put("year", String.valueOf(split_genre.year));

											runTraceData.put("genre1", String.valueOf(split_genre.genre1));

											runTraceData.put("genre2", String.valueOf(split_genre.genre2));

											runTraceData.put("genre3", String.valueOf(split_genre.genre3));

											runTraceData.put("genre4", String.valueOf(split_genre.genre4));

											runTraceData.put("genre5", String.valueOf(split_genre.genre5));

											runTraceData.put("genre6", String.valueOf(split_genre.genre6));

											runTrace.sendTrace("split_genre", "tFileInputDelimited_2", runTraceData);
										}

									}

									tos_count_tMap_5++;

									/**
									 * [tMap_5 main ] stop
									 */

									/**
									 * [tMap_5 process_data_begin ] start
									 */

									currentComponent = "tMap_5";

									/**
									 * [tMap_5 process_data_begin ] stop
									 */
// Start of branch "split_genre"
									if (split_genre != null) {

										/**
										 * [tFlowMeter_5 main ] start
										 */

										currentComponent = "tFlowMeter_5";

										if (execStat) {
											runStat.updateStatOnConnection(iterateId, 1, 1

													, "split_genre"

											);
										}

										count_tFlowMeter_5++;

										row7 = split_genre;

										if (row7 != null) {
											globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2",
													Boolean.TRUE);
											if (runTrace.isPause()) {
												while (runTrace.isPause()) {
													Thread.sleep(100);
												}
											} else {

												// here we dump the line content for trace purpose
												java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

												runTraceData.put("movieId", String.valueOf(row7.movieId));

												runTraceData.put("cleaned_title", String.valueOf(row7.cleaned_title));

												runTraceData.put("year", String.valueOf(row7.year));

												runTraceData.put("genre1", String.valueOf(row7.genre1));

												runTraceData.put("genre2", String.valueOf(row7.genre2));

												runTraceData.put("genre3", String.valueOf(row7.genre3));

												runTraceData.put("genre4", String.valueOf(row7.genre4));

												runTraceData.put("genre5", String.valueOf(row7.genre5));

												runTraceData.put("genre6", String.valueOf(row7.genre6));

												runTrace.sendTrace("row7", "tFileInputDelimited_2", runTraceData);
											}

										}

										tos_count_tFlowMeter_5++;

										/**
										 * [tFlowMeter_5 main ] stop
										 */

										/**
										 * [tFlowMeter_5 process_data_begin ] start
										 */

										currentComponent = "tFlowMeter_5";

										/**
										 * [tFlowMeter_5 process_data_begin ] stop
										 */

										/**
										 * [tDBOutput_1 main ] start
										 */

										currentComponent = "tDBOutput_1";

										if (execStat) {
											runStat.updateStatOnConnection(iterateId, 1, 1

													, "row7"

											);
										}

										whetherReject_tDBOutput_1 = false;
										pstmt_tDBOutput_1.setInt(1, row7.movieId);

										if (row7.cleaned_title == null) {
											pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(2, row7.cleaned_title);
										}

										if (row7.year == null) {
											pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(3, row7.year);
										}

										if (row7.genre1 == null) {
											pstmt_tDBOutput_1.setNull(4, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(4, row7.genre1);
										}

										if (row7.genre2 == null) {
											pstmt_tDBOutput_1.setNull(5, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(5, row7.genre2);
										}

										if (row7.genre3 == null) {
											pstmt_tDBOutput_1.setNull(6, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(6, row7.genre3);
										}

										if (row7.genre4 == null) {
											pstmt_tDBOutput_1.setNull(7, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(7, row7.genre4);
										}

										if (row7.genre5 == null) {
											pstmt_tDBOutput_1.setNull(8, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(8, row7.genre5);
										}

										if (row7.genre6 == null) {
											pstmt_tDBOutput_1.setNull(9, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(9, row7.genre6);
										}

										pstmt_tDBOutput_1.addBatch();
										nb_line_tDBOutput_1++;

										batchSizeCounter_tDBOutput_1++;

										////////// batch execute by batch size///////
										class LimitBytesHelper_tDBOutput_1 {
											public int limitBytePart1(int counter,
													java.sql.PreparedStatement pstmt_tDBOutput_1) throws Exception {
												try {

													for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
														if (countEach_tDBOutput_1 == -2
																|| countEach_tDBOutput_1 == -3) {
															break;
														}
														counter += countEach_tDBOutput_1;
													}

												} catch (java.sql.BatchUpdateException e) {
													globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());

													int countSum_tDBOutput_1 = 0;
													for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
														counter += (countEach_tDBOutput_1 < 0 ? 0
																: countEach_tDBOutput_1);
													}

													System.err.println(e.getMessage());

												}
												return counter;
											}

											public int limitBytePart2(int counter,
													java.sql.PreparedStatement pstmt_tDBOutput_1) throws Exception {
												try {

													for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
														if (countEach_tDBOutput_1 == -2
																|| countEach_tDBOutput_1 == -3) {
															break;
														}
														counter += countEach_tDBOutput_1;
													}

												} catch (java.sql.BatchUpdateException e) {
													globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());

													for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
														counter += (countEach_tDBOutput_1 < 0 ? 0
																: countEach_tDBOutput_1);
													}

													System.err.println(e.getMessage());

												}
												return counter;
											}
										}
										if ((batchSize_tDBOutput_1 > 0)
												&& (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1)) {

											insertedCount_tDBOutput_1 = new LimitBytesHelper_tDBOutput_1()
													.limitBytePart1(insertedCount_tDBOutput_1, pstmt_tDBOutput_1);
											rowsToCommitCount_tDBOutput_1 = insertedCount_tDBOutput_1;

											batchSizeCounter_tDBOutput_1 = 0;
										}

										//////////// commit every////////////

										commitCounter_tDBOutput_1++;
										if (commitEvery_tDBOutput_1 <= commitCounter_tDBOutput_1) {
											if ((batchSize_tDBOutput_1 > 0) && (batchSizeCounter_tDBOutput_1 > 0)) {

												insertedCount_tDBOutput_1 = new LimitBytesHelper_tDBOutput_1()
														.limitBytePart1(insertedCount_tDBOutput_1, pstmt_tDBOutput_1);

												batchSizeCounter_tDBOutput_1 = 0;
											}
											if (rowsToCommitCount_tDBOutput_1 != 0) {

											}
											conn_tDBOutput_1.commit();
											if (rowsToCommitCount_tDBOutput_1 != 0) {

												rowsToCommitCount_tDBOutput_1 = 0;
											}
											commitCounter_tDBOutput_1 = 0;
										}

										tos_count_tDBOutput_1++;

										/**
										 * [tDBOutput_1 main ] stop
										 */

										/**
										 * [tDBOutput_1 process_data_begin ] start
										 */

										currentComponent = "tDBOutput_1";

										/**
										 * [tDBOutput_1 process_data_begin ] stop
										 */

										/**
										 * [tDBOutput_1 process_data_end ] start
										 */

										currentComponent = "tDBOutput_1";

										/**
										 * [tDBOutput_1 process_data_end ] stop
										 */

										/**
										 * [tFlowMeter_5 process_data_end ] start
										 */

										currentComponent = "tFlowMeter_5";

										/**
										 * [tFlowMeter_5 process_data_end ] stop
										 */

									} // End of branch "split_genre"

									/**
									 * [tMap_5 process_data_end ] start
									 */

									currentComponent = "tMap_5";

									/**
									 * [tMap_5 process_data_end ] stop
									 */

								} // End of branch "year_valid"

								/**
								 * [tMap_4 process_data_end ] start
								 */

								currentComponent = "tMap_4";

								/**
								 * [tMap_4 process_data_end ] stop
								 */

							} // End of branch "row4"

// Start of branch "row5"
							if (row5 != null) {

								/**
								 * [tLogRow_2 main ] start
								 */

								currentComponent = "tLogRow_2";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row5"

									);
								}

///////////////////////		

								String[] row_tLogRow_2 = new String[5];

								row_tLogRow_2[0] = String.valueOf(row5.movieId);

								if (row5.title != null) { //
									row_tLogRow_2[1] = String.valueOf(row5.title);

								} //

								if (row5.genres != null) { //
									row_tLogRow_2[2] = String.valueOf(row5.genres);

								} //

								if (row5.errorCode != null) { //
									row_tLogRow_2[3] = String.valueOf(row5.errorCode);

								} //

								if (row5.errorMessage != null) { //
									row_tLogRow_2[4] = String.valueOf(row5.errorMessage);

								} //

								util_tLogRow_2.addRow(row_tLogRow_2);
								nb_line_tLogRow_2++;
//////

//////                    

///////////////////////    			

								row6 = row5;

								if (row6 != null) {
									globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2", Boolean.TRUE);
									if (runTrace.isPause()) {
										while (runTrace.isPause()) {
											Thread.sleep(100);
										}
									} else {

										// here we dump the line content for trace purpose
										java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

										runTraceData.put("movieId", String.valueOf(row6.movieId));

										runTraceData.put("title", String.valueOf(row6.title));

										runTraceData.put("genres", String.valueOf(row6.genres));

										runTraceData.put("errorCode", String.valueOf(row6.errorCode));

										runTraceData.put("errorMessage", String.valueOf(row6.errorMessage));

										runTrace.sendTrace("row6", "tFileInputDelimited_2", runTraceData);
									}

								}

								tos_count_tLogRow_2++;

								/**
								 * [tLogRow_2 main ] stop
								 */

								/**
								 * [tLogRow_2 process_data_begin ] start
								 */

								currentComponent = "tLogRow_2";

								/**
								 * [tLogRow_2 process_data_begin ] stop
								 */

								/**
								 * [tLogRow_3 main ] start
								 */

								currentComponent = "tLogRow_3";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row6"

									);
								}

///////////////////////		

								strBuffer_tLogRow_3 = new StringBuilder();

								strBuffer_tLogRow_3.append(String.valueOf(row6.movieId));

								strBuffer_tLogRow_3.append("|");

								if (row6.title != null) { //

									strBuffer_tLogRow_3.append(String.valueOf(row6.title));

								} //

								strBuffer_tLogRow_3.append("|");

								if (row6.genres != null) { //

									strBuffer_tLogRow_3.append(String.valueOf(row6.genres));

								} //

								strBuffer_tLogRow_3.append("|");

								if (row6.errorCode != null) { //

									strBuffer_tLogRow_3.append(String.valueOf(row6.errorCode));

								} //

								strBuffer_tLogRow_3.append("|");

								if (row6.errorMessage != null) { //

									strBuffer_tLogRow_3.append(String.valueOf(row6.errorMessage));

								} //

								if (globalMap.get("tLogRow_CONSOLE") != null) {
									consoleOut_tLogRow_3 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
								} else {
									consoleOut_tLogRow_3 = new java.io.PrintStream(
											new java.io.BufferedOutputStream(System.out));
									globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_3);
								}
								consoleOut_tLogRow_3.println(strBuffer_tLogRow_3.toString());
								consoleOut_tLogRow_3.flush();
								nb_line_tLogRow_3++;
//////

//////                    

///////////////////////    			

								tos_count_tLogRow_3++;

								/**
								 * [tLogRow_3 main ] stop
								 */

								/**
								 * [tLogRow_3 process_data_begin ] start
								 */

								currentComponent = "tLogRow_3";

								/**
								 * [tLogRow_3 process_data_begin ] stop
								 */

								/**
								 * [tLogRow_3 process_data_end ] start
								 */

								currentComponent = "tLogRow_3";

								/**
								 * [tLogRow_3 process_data_end ] stop
								 */

								/**
								 * [tLogRow_2 process_data_end ] start
								 */

								currentComponent = "tLogRow_2";

								/**
								 * [tLogRow_2 process_data_end ] stop
								 */

							} // End of branch "row5"

							/**
							 * [tSchemaComplianceCheck_1 process_data_end ] start
							 */

							currentComponent = "tSchemaComplianceCheck_1";

							/**
							 * [tSchemaComplianceCheck_1 process_data_end ] stop
							 */

							/**
							 * [tLogRow_1 process_data_end ] start
							 */

							currentComponent = "tLogRow_1";

							/**
							 * [tLogRow_1 process_data_end ] stop
							 */

						} // End of branch "row1"

						/**
						 * [tFileInputDelimited_2 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						/**
						 * [tFileInputDelimited_2 process_data_end ] stop
						 */

						if (!isChildJob && (Boolean) globalMap.get("ENABLE_TRACES_CONNECTION_tFileInputDelimited_2")) {
							if (globalMap.get("USE_CONDITION") != null && (Boolean) globalMap.get("USE_CONDITION")) {
								if (globalMap.get("TRACE_CONDITION") != null
										&& (Boolean) globalMap.get("TRACE_CONDITION")) {
									// if next breakpoint has been clicked on UI or if start job, should wait action
									// of user.
									if (runTrace.isNextBreakpoint()) {
										runTrace.waitForUserAction();
									} else if (runTrace.isNextRow()) {
										runTrace.waitForUserAction();
									}
								} else {
									// if next row has been clicked on UI or if start job, should wait action of
									// user.
									if (runTrace.isNextRow()) {
										runTrace.waitForUserAction();
									}
								}
							} else { // no condition set
								if (runTrace.isNextRow()) {
									runTrace.waitForUserAction();
								} else {
									Thread.sleep(1000);
								}
							}

						}
						globalMap.put("USE_CONDITION", Boolean.FALSE);

						/**
						 * [tFileInputDelimited_2 end ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						nb_line_tFileInputDelimited_2++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_2 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_2 != null) {
							csvReadertFileInputDelimited_2.close();
						}
					}
					if (csvReadertFileInputDelimited_2 != null) {
						globalMap.put("tFileInputDelimited_2_NB_LINE", nb_line_tFileInputDelimited_2);
					}

				}

				ok_Hash.put("tFileInputDelimited_2", true);
				end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_2 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				currentComponent = "tLogRow_1";

//////

				java.io.PrintStream consoleOut_tLogRow_1 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_1 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_1 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_1);
				}

				consoleOut_tLogRow_1.println(util_tLogRow_1.format().toString());
				consoleOut_tLogRow_1.flush();
//////
				globalMap.put("tLogRow_1_NB_LINE", nb_line_tLogRow_1);

///////////////////////    			

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
				}

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

				/**
				 * [tSchemaComplianceCheck_1 end ] start
				 */

				currentComponent = "tSchemaComplianceCheck_1";

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
				}

				ok_Hash.put("tSchemaComplianceCheck_1", true);
				end_Hash.put("tSchemaComplianceCheck_1", System.currentTimeMillis());

				/**
				 * [tSchemaComplianceCheck_1 end ] stop
				 */

				/**
				 * [tMap_4 end ] start
				 */

				currentComponent = "tMap_4";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row4");
				}

				ok_Hash.put("tMap_4", true);
				end_Hash.put("tMap_4", System.currentTimeMillis());

				/**
				 * [tMap_4 end ] stop
				 */

				/**
				 * [tMap_5 end ] start
				 */

				currentComponent = "tMap_5";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "year_valid");
				}

				ok_Hash.put("tMap_5", true);
				end_Hash.put("tMap_5", System.currentTimeMillis());

				/**
				 * [tMap_5 end ] stop
				 */

				/**
				 * [tFlowMeter_5 end ] start
				 */

				currentComponent = "tFlowMeter_5";

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "split_genre");
				}

				ok_Hash.put("tFlowMeter_5", true);
				end_Hash.put("tFlowMeter_5", System.currentTimeMillis());

				/**
				 * [tFlowMeter_5 end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					int countSum_tDBOutput_1 = 0;
					if (pstmt_tDBOutput_1 != null && batchSizeCounter_tDBOutput_1 > 0) {

						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							if (countEach_tDBOutput_1 == -2 || countEach_tDBOutput_1 == -3) {
								break;
							}
							countSum_tDBOutput_1 += countEach_tDBOutput_1;
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					}

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

				} catch (java.sql.BatchUpdateException e) {
					globalMap.put("tDBOutput_1_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_1 = 0;
					for (int countEach_tDBOutput_1 : e.getUpdateCounts()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					System.err.println(e.getMessage());

				}
				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");

				}
				resourceMap.put("statementClosed_tDBOutput_1", true);
				if (rowsToCommitCount_tDBOutput_1 != 0) {

				}
				conn_tDBOutput_1.commit();
				if (rowsToCommitCount_tDBOutput_1 != 0) {

					rowsToCommitCount_tDBOutput_1 = 0;
				}
				commitCounter_tDBOutput_1 = 0;
				conn_tDBOutput_1.close();
				resourceMap.put("finish_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row7");
				}

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

				/**
				 * [tLogRow_2 end ] start
				 */

				currentComponent = "tLogRow_2";

//////

				java.io.PrintStream consoleOut_tLogRow_2 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_2 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_2 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_2);
				}

				consoleOut_tLogRow_2.println(util_tLogRow_2.format().toString());
				consoleOut_tLogRow_2.flush();
//////
				globalMap.put("tLogRow_2_NB_LINE", nb_line_tLogRow_2);

///////////////////////    			

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row5");
				}

				ok_Hash.put("tLogRow_2", true);
				end_Hash.put("tLogRow_2", System.currentTimeMillis());

				/**
				 * [tLogRow_2 end ] stop
				 */

				/**
				 * [tLogRow_3 end ] start
				 */

				currentComponent = "tLogRow_3";

//////
//////
				globalMap.put("tLogRow_3_NB_LINE", nb_line_tLogRow_3);

///////////////////////    			

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row6");
				}

				ok_Hash.put("tLogRow_3", true);
				end_Hash.put("tLogRow_3", System.currentTimeMillis());

				/**
				 * [tLogRow_3 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_2 finally ] start
				 */

				currentComponent = "tFileInputDelimited_2";

				/**
				 * [tFileInputDelimited_2 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				currentComponent = "tLogRow_1";

				/**
				 * [tLogRow_1 finally ] stop
				 */

				/**
				 * [tSchemaComplianceCheck_1 finally ] start
				 */

				currentComponent = "tSchemaComplianceCheck_1";

				/**
				 * [tSchemaComplianceCheck_1 finally ] stop
				 */

				/**
				 * [tMap_4 finally ] start
				 */

				currentComponent = "tMap_4";

				/**
				 * [tMap_4 finally ] stop
				 */

				/**
				 * [tMap_5 finally ] start
				 */

				currentComponent = "tMap_5";

				/**
				 * [tMap_5 finally ] stop
				 */

				/**
				 * [tFlowMeter_5 finally ] start
				 */

				currentComponent = "tFlowMeter_5";

				/**
				 * [tFlowMeter_5 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
						if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_1")) != null) {
							pstmtToClose_tDBOutput_1.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_1") == null) {
						java.sql.Connection ctn_tDBOutput_1 = null;
						if ((ctn_tDBOutput_1 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_1")) != null) {
							try {
								ctn_tDBOutput_1.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_1) {
								String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :"
										+ sqlEx_tDBOutput_1.getMessage();
								System.err.println(errorMessage_tDBOutput_1);
							}
						}
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

				/**
				 * [tLogRow_2 finally ] start
				 */

				currentComponent = "tLogRow_2";

				/**
				 * [tLogRow_2 finally ] stop
				 */

				/**
				 * [tLogRow_3 finally ] start
				 */

				currentComponent = "tLogRow_3";

				/**
				 * [tLogRow_3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 1);
	}

	public static class row9Struct implements routines.system.IPersistableRow<row9Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("userId=" + String.valueOf(userId));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row9Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class DimUserStruct implements routines.system.IPersistableRow<DimUserStruct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("userId=" + String.valueOf(userId));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(DimUserStruct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row8Struct implements routines.system.IPersistableRow<row8Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public String rating;

		public String getRating() {
			return this.rating;
		}

		public String timestamp;

		public String getTimestamp() {
			return this.timestamp;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PIPLINE_COMPLITE_tParallelize.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_tParallelize.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_tParallelize, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					this.rating = readString(dis);

					this.timestamp = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					this.rating = readString(dis);

					this.timestamp = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// String

				writeString(this.rating, dos);

				// String

				writeString(this.timestamp, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// String

				writeString(this.rating, dos);

				// String

				writeString(this.timestamp, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("userId=" + String.valueOf(userId));
			sb.append(",movieId=" + String.valueOf(movieId));
			sb.append(",rating=" + rating);
			sb.append(",timestamp=" + timestamp);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row8Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row8Struct row8 = new row8Struct();
				DimUserStruct DimUser = new DimUserStruct();
				row9Struct row9 = new row9Struct();

				/**
				 * [tDBOutput_2 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_3", Boolean.FALSE);

				ok_Hash.put("tDBOutput_2", false);
				start_Hash.put("tDBOutput_2", System.currentTimeMillis());

				currentComponent = "tDBOutput_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row9");
				}

				int tos_count_tDBOutput_2 = 0;

				int nb_line_tDBOutput_2 = 0;
				int nb_line_update_tDBOutput_2 = 0;
				int nb_line_inserted_tDBOutput_2 = 0;
				int nb_line_deleted_tDBOutput_2 = 0;
				int nb_line_rejected_tDBOutput_2 = 0;

				int deletedCount_tDBOutput_2 = 0;
				int updatedCount_tDBOutput_2 = 0;
				int insertedCount_tDBOutput_2 = 0;
				int rowsToCommitCount_tDBOutput_2 = 0;
				int rejectedCount_tDBOutput_2 = 0;
				String dbschema_tDBOutput_2 = null;
				String tableName_tDBOutput_2 = null;
				boolean whetherReject_tDBOutput_2 = false;

				java.util.Calendar calendar_tDBOutput_2 = java.util.Calendar.getInstance();
				long year1_tDBOutput_2 = TalendDate.parseDate("yyyy-MM-dd", "0001-01-01").getTime();
				long year2_tDBOutput_2 = TalendDate.parseDate("yyyy-MM-dd", "1753-01-01").getTime();
				long year10000_tDBOutput_2 = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", "9999-12-31 24:00:00")
						.getTime();
				long date_tDBOutput_2;

				java.util.Calendar calendar_datetimeoffset_tDBOutput_2 = java.util.Calendar
						.getInstance(java.util.TimeZone.getTimeZone("UTC"));

				java.sql.Connection conn_tDBOutput_2 = null;
				String dbUser_tDBOutput_2 = null;
				dbschema_tDBOutput_2 = "dbo";
				String driverClass_tDBOutput_2 = "net.sourceforge.jtds.jdbc.Driver";

				java.lang.Class.forName(driverClass_tDBOutput_2);
				String port_tDBOutput_2 = "1433";
				String dbname_tDBOutput_2 = "rabii";
				String url_tDBOutput_2 = "jdbc:jtds:sqlserver://" + "localhost";
				if (!"".equals(port_tDBOutput_2)) {
					url_tDBOutput_2 += ":" + "1433";
				}
				if (!"".equals(dbname_tDBOutput_2)) {
					url_tDBOutput_2 += "//" + "rabii";

				}
				url_tDBOutput_2 += ";appName=" + projectName + ";" + "instance=SQLEXPRESS";
				dbUser_tDBOutput_2 = "sa";

				final String decryptedPassword_tDBOutput_2 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:ZOLZp4Vyf7g1c27wknOdg1PND9qyQBoj/4aLUqhH3KHwL5A=");

				String dbPwd_tDBOutput_2 = decryptedPassword_tDBOutput_2;
				conn_tDBOutput_2 = java.sql.DriverManager.getConnection(url_tDBOutput_2, dbUser_tDBOutput_2,
						dbPwd_tDBOutput_2);

				resourceMap.put("conn_tDBOutput_2", conn_tDBOutput_2);

				conn_tDBOutput_2.setAutoCommit(false);
				int commitEvery_tDBOutput_2 = 10000;
				int commitCounter_tDBOutput_2 = 0;

				int batchSize_tDBOutput_2 = 10000;
				int batchSizeCounter_tDBOutput_2 = 0;

				if (dbschema_tDBOutput_2 == null || dbschema_tDBOutput_2.trim().length() == 0) {
					tableName_tDBOutput_2 = "user";
				} else {
					tableName_tDBOutput_2 = dbschema_tDBOutput_2 + "].[" + "user";
				}
				int count_tDBOutput_2 = 0;

				boolean whetherExist_tDBOutput_2 = false;
				try (java.sql.Statement isExistStmt_tDBOutput_2 = conn_tDBOutput_2.createStatement()) {
					try {
						isExistStmt_tDBOutput_2.execute("SELECT TOP 1 1 FROM [" + tableName_tDBOutput_2 + "]");
						whetherExist_tDBOutput_2 = true;
					} catch (java.lang.Exception e) {
						globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());
						whetherExist_tDBOutput_2 = false;
					}
				}
				if (!whetherExist_tDBOutput_2) {
					try (java.sql.Statement stmtCreate_tDBOutput_2 = conn_tDBOutput_2.createStatement()) {
						stmtCreate_tDBOutput_2.execute("CREATE TABLE [" + tableName_tDBOutput_2 + "]([userId] INT )");
					}
				}
				String insert_tDBOutput_2 = "INSERT INTO [" + tableName_tDBOutput_2 + "] ([userId]) VALUES (?)";
				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(insert_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tUniqRow_2 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_3", Boolean.FALSE);

				ok_Hash.put("tUniqRow_2", false);
				start_Hash.put("tUniqRow_2", System.currentTimeMillis());

				currentComponent = "tUniqRow_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "DimUser");
				}

				int tos_count_tUniqRow_2 = 0;

				class KeyStruct_tUniqRow_2 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					Integer userId;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_2 other = (KeyStruct_tUniqRow_2) obj;

						if (this.userId == null) {
							if (other.userId != null)
								return false;

						} else if (!this.userId.equals(other.userId))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_2 = 0;
				int nb_duplicates_tUniqRow_2 = 0;
				KeyStruct_tUniqRow_2 finder_tUniqRow_2 = new KeyStruct_tUniqRow_2();
				java.util.Set<KeyStruct_tUniqRow_2> keystUniqRow_2 = new java.util.HashSet<KeyStruct_tUniqRow_2>();

				/**
				 * [tUniqRow_2 begin ] stop
				 */

				/**
				 * [tMap_6 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_3", Boolean.FALSE);

				ok_Hash.put("tMap_6", false);
				start_Hash.put("tMap_6", System.currentTimeMillis());

				currentComponent = "tMap_6";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row8");
				}

				int tos_count_tMap_6 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_6__Struct {
				}
				Var__tMap_6__Struct Var__tMap_6 = new Var__tMap_6__Struct();
// ###############################

// ###############################
// # Outputs initialization
				DimUserStruct DimUser_tmp = new DimUserStruct();
// ###############################

				/**
				 * [tMap_6 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_3 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_3", Boolean.FALSE);

				ok_Hash.put("tFileInputDelimited_3", false);
				start_Hash.put("tFileInputDelimited_3", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_3";

				int tos_count_tFileInputDelimited_3 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_3 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_3 = 0;
				int footer_tFileInputDelimited_3 = 0;
				int totalLinetFileInputDelimited_3 = 0;
				int limittFileInputDelimited_3 = -1;
				int lastLinetFileInputDelimited_3 = -1;

				char fieldSeparator_tFileInputDelimited_3[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ",").length() > 0) {
					fieldSeparator_tFileInputDelimited_3 = ((String) ",").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_3[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_3 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_3 = /** Start field tFileInputDelimited_3:FILENAME */
						"C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/input/ratings.csv"/**
																															 * End
																															 * field
																															 * tFileInputDelimited_3:FILENAME
																															 */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_3 = null;

				try {

					String[] rowtFileInputDelimited_3 = null;
					int currentLinetFileInputDelimited_3 = 0;
					int outputLinetFileInputDelimited_3 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_3 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_3 = 0;
							if (footer_value_tFileInputDelimited_3 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_3 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_3,
									fieldSeparator_tFileInputDelimited_3[0], "UTF-8");
						} else {
							csvReadertFileInputDelimited_3 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_3),
									fieldSeparator_tFileInputDelimited_3[0], "UTF-8");
						}

						csvReadertFileInputDelimited_3.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_3[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_3[0] != '\r'))
							csvReadertFileInputDelimited_3.setLineEnd("" + rowSeparator_tFileInputDelimited_3[0]);

						csvReadertFileInputDelimited_3.setQuoteChar('\"');

						csvReadertFileInputDelimited_3.setEscapeChar(csvReadertFileInputDelimited_3.getQuoteChar());

						if (footer_tFileInputDelimited_3 > 0) {
							for (totalLinetFileInputDelimited_3 = 0; totalLinetFileInputDelimited_3 < 1; totalLinetFileInputDelimited_3++) {
								csvReadertFileInputDelimited_3.readNext();
							}
							csvReadertFileInputDelimited_3.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_3.readNext()) {

								totalLinetFileInputDelimited_3++;

							}
							int lastLineTemptFileInputDelimited_3 = totalLinetFileInputDelimited_3
									- footer_tFileInputDelimited_3 < 0 ? 0
											: totalLinetFileInputDelimited_3 - footer_tFileInputDelimited_3;
							if (lastLinetFileInputDelimited_3 > 0) {
								lastLinetFileInputDelimited_3 = lastLinetFileInputDelimited_3 < lastLineTemptFileInputDelimited_3
										? lastLinetFileInputDelimited_3
										: lastLineTemptFileInputDelimited_3;
							} else {
								lastLinetFileInputDelimited_3 = lastLineTemptFileInputDelimited_3;
							}

							csvReadertFileInputDelimited_3.close();
							if (filename_tFileInputDelimited_3 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_3 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_3,
										fieldSeparator_tFileInputDelimited_3[0], "UTF-8");
							} else {
								csvReadertFileInputDelimited_3 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_3),
										fieldSeparator_tFileInputDelimited_3[0], "UTF-8");
							}
							csvReadertFileInputDelimited_3.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_3[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_3[0] != '\r'))
								csvReadertFileInputDelimited_3.setLineEnd("" + rowSeparator_tFileInputDelimited_3[0]);

							csvReadertFileInputDelimited_3.setQuoteChar('\"');

							csvReadertFileInputDelimited_3.setEscapeChar(csvReadertFileInputDelimited_3.getQuoteChar());

						}

						if (limittFileInputDelimited_3 != 0) {
							for (currentLinetFileInputDelimited_3 = 0; currentLinetFileInputDelimited_3 < 1; currentLinetFileInputDelimited_3++) {
								csvReadertFileInputDelimited_3.readNext();
							}
						}
						csvReadertFileInputDelimited_3.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_3 != 0 && csvReadertFileInputDelimited_3 != null
							&& csvReadertFileInputDelimited_3.readNext()) {
						rowstate_tFileInputDelimited_3.reset();

						rowtFileInputDelimited_3 = csvReadertFileInputDelimited_3.getValues();

						currentLinetFileInputDelimited_3++;

						if (lastLinetFileInputDelimited_3 > -1
								&& currentLinetFileInputDelimited_3 > lastLinetFileInputDelimited_3) {
							break;
						}
						outputLinetFileInputDelimited_3++;
						if (limittFileInputDelimited_3 > 0
								&& outputLinetFileInputDelimited_3 > limittFileInputDelimited_3) {
							break;
						}

						row8 = null;

						boolean whetherReject_tFileInputDelimited_3 = false;
						row8 = new row8Struct();
						try {

							char fieldSeparator_tFileInputDelimited_3_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ",").length() > 0) {
								fieldSeparator_tFileInputDelimited_3_ListType = ((String) ",").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_3.length == 1 && ("\015").equals(rowtFileInputDelimited_3[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row8.userId = null;

								row8.movieId = null;

								row8.rating = null;

								row8.timestamp = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_3 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_3 = 0;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row8.userId = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3]);

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"userId", "row8",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row8.userId = null;

									}

								} else {

									row8.userId = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 1;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									if (rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3].length() > 0) {
										try {

											row8.movieId = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3]);

										} catch (java.lang.Exception ex_tFileInputDelimited_3) {
											globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE",
													ex_tFileInputDelimited_3.getMessage());
											rowstate_tFileInputDelimited_3.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"movieId", "row8",
															rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3],
															ex_tFileInputDelimited_3),
													ex_tFileInputDelimited_3));
										}
									} else {

										row8.movieId = null;

									}

								} else {

									row8.movieId = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 2;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row8.rating = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row8.rating = null;

								}

								columnIndexWithD_tFileInputDelimited_3 = 3;

								if (columnIndexWithD_tFileInputDelimited_3 < rowtFileInputDelimited_3.length) {

									row8.timestamp = rowtFileInputDelimited_3[columnIndexWithD_tFileInputDelimited_3];

								} else {

									row8.timestamp = null;

								}

							}

							if (rowstate_tFileInputDelimited_3.getException() != null) {
								throw rowstate_tFileInputDelimited_3.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_3 = true;

							System.err.println(e.getMessage());
							row8 = null;

							globalMap.put("tFileInputDelimited_3_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_3 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_3 main ] start
						 */

						currentComponent = "tFileInputDelimited_3";

						if (row8 != null) {
							globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_3", Boolean.TRUE);
							if (runTrace.isPause()) {
								while (runTrace.isPause()) {
									Thread.sleep(100);
								}
							} else {

								// here we dump the line content for trace purpose
								java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

								runTraceData.put("userId", String.valueOf(row8.userId));

								runTraceData.put("movieId", String.valueOf(row8.movieId));

								runTraceData.put("rating", String.valueOf(row8.rating));

								runTraceData.put("timestamp", String.valueOf(row8.timestamp));

								runTrace.sendTrace("row8", "tFileInputDelimited_3", runTraceData);
							}

						}

						tos_count_tFileInputDelimited_3++;

						/**
						 * [tFileInputDelimited_3 main ] stop
						 */

						/**
						 * [tFileInputDelimited_3 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_3";

						/**
						 * [tFileInputDelimited_3 process_data_begin ] stop
						 */
// Start of branch "row8"
						if (row8 != null) {

							/**
							 * [tMap_6 main ] start
							 */

							currentComponent = "tMap_6";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row8"

								);
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_6 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_6 = false;
							boolean mainRowRejected_tMap_6 = false;

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_6__Struct Var = Var__tMap_6;// ###############################
								// ###############################
								// # Output tables

								DimUser = null;

// # Output table : 'DimUser'
								DimUser_tmp.userId = row8.userId;
								DimUser = DimUser_tmp;
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_6 = false;

							if (DimUser != null) {
								globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_3", Boolean.TRUE);
								if (runTrace.isPause()) {
									while (runTrace.isPause()) {
										Thread.sleep(100);
									}
								} else {

									// here we dump the line content for trace purpose
									java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

									runTraceData.put("userId", String.valueOf(DimUser.userId));

									runTrace.sendTrace("DimUser", "tFileInputDelimited_3", runTraceData);
								}

							}

							tos_count_tMap_6++;

							/**
							 * [tMap_6 main ] stop
							 */

							/**
							 * [tMap_6 process_data_begin ] start
							 */

							currentComponent = "tMap_6";

							/**
							 * [tMap_6 process_data_begin ] stop
							 */
// Start of branch "DimUser"
							if (DimUser != null) {

								/**
								 * [tUniqRow_2 main ] start
								 */

								currentComponent = "tUniqRow_2";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "DimUser"

									);
								}

								row9 = null;
								finder_tUniqRow_2.userId = DimUser.userId;
								finder_tUniqRow_2.hashCodeDirty = true;
								if (!keystUniqRow_2.contains(finder_tUniqRow_2)) {
									KeyStruct_tUniqRow_2 new_tUniqRow_2 = new KeyStruct_tUniqRow_2();

									new_tUniqRow_2.userId = DimUser.userId;

									keystUniqRow_2.add(new_tUniqRow_2);
									if (row9 == null) {

										row9 = new row9Struct();
									}
									row9.userId = DimUser.userId;
									nb_uniques_tUniqRow_2++;
								} else {
									nb_duplicates_tUniqRow_2++;
								}

								if (row9 != null) {
									globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_3", Boolean.TRUE);
									if (runTrace.isPause()) {
										while (runTrace.isPause()) {
											Thread.sleep(100);
										}
									} else {

										// here we dump the line content for trace purpose
										java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

										runTraceData.put("userId", String.valueOf(row9.userId));

										runTrace.sendTrace("row9", "tFileInputDelimited_3", runTraceData);
									}

								}

								tos_count_tUniqRow_2++;

								/**
								 * [tUniqRow_2 main ] stop
								 */

								/**
								 * [tUniqRow_2 process_data_begin ] start
								 */

								currentComponent = "tUniqRow_2";

								/**
								 * [tUniqRow_2 process_data_begin ] stop
								 */
// Start of branch "row9"
								if (row9 != null) {

									/**
									 * [tDBOutput_2 main ] start
									 */

									currentComponent = "tDBOutput_2";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "row9"

										);
									}

									whetherReject_tDBOutput_2 = false;
									if (row9.userId == null) {
										pstmt_tDBOutput_2.setNull(1, java.sql.Types.INTEGER);
									} else {
										pstmt_tDBOutput_2.setInt(1, row9.userId);
									}

									pstmt_tDBOutput_2.addBatch();
									nb_line_tDBOutput_2++;

									batchSizeCounter_tDBOutput_2++;

									////////// batch execute by batch size///////
									class LimitBytesHelper_tDBOutput_2 {
										public int limitBytePart1(int counter,
												java.sql.PreparedStatement pstmt_tDBOutput_2) throws Exception {
											try {

												for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
													if (countEach_tDBOutput_2 == -2 || countEach_tDBOutput_2 == -3) {
														break;
													}
													counter += countEach_tDBOutput_2;
												}

											} catch (java.sql.BatchUpdateException e) {
												globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());

												int countSum_tDBOutput_2 = 0;
												for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
													counter += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
												}

												System.err.println(e.getMessage());

											}
											return counter;
										}

										public int limitBytePart2(int counter,
												java.sql.PreparedStatement pstmt_tDBOutput_2) throws Exception {
											try {

												for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
													if (countEach_tDBOutput_2 == -2 || countEach_tDBOutput_2 == -3) {
														break;
													}
													counter += countEach_tDBOutput_2;
												}

											} catch (java.sql.BatchUpdateException e) {
												globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());

												for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
													counter += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
												}

												System.err.println(e.getMessage());

											}
											return counter;
										}
									}
									if ((batchSize_tDBOutput_2 > 0)
											&& (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2)) {

										insertedCount_tDBOutput_2 = new LimitBytesHelper_tDBOutput_2()
												.limitBytePart1(insertedCount_tDBOutput_2, pstmt_tDBOutput_2);
										rowsToCommitCount_tDBOutput_2 = insertedCount_tDBOutput_2;

										batchSizeCounter_tDBOutput_2 = 0;
									}

									//////////// commit every////////////

									commitCounter_tDBOutput_2++;
									if (commitEvery_tDBOutput_2 <= commitCounter_tDBOutput_2) {
										if ((batchSize_tDBOutput_2 > 0) && (batchSizeCounter_tDBOutput_2 > 0)) {

											insertedCount_tDBOutput_2 = new LimitBytesHelper_tDBOutput_2()
													.limitBytePart1(insertedCount_tDBOutput_2, pstmt_tDBOutput_2);

											batchSizeCounter_tDBOutput_2 = 0;
										}
										if (rowsToCommitCount_tDBOutput_2 != 0) {

										}
										conn_tDBOutput_2.commit();
										if (rowsToCommitCount_tDBOutput_2 != 0) {

											rowsToCommitCount_tDBOutput_2 = 0;
										}
										commitCounter_tDBOutput_2 = 0;
									}

									tos_count_tDBOutput_2++;

									/**
									 * [tDBOutput_2 main ] stop
									 */

									/**
									 * [tDBOutput_2 process_data_begin ] start
									 */

									currentComponent = "tDBOutput_2";

									/**
									 * [tDBOutput_2 process_data_begin ] stop
									 */

									/**
									 * [tDBOutput_2 process_data_end ] start
									 */

									currentComponent = "tDBOutput_2";

									/**
									 * [tDBOutput_2 process_data_end ] stop
									 */

								} // End of branch "row9"

								/**
								 * [tUniqRow_2 process_data_end ] start
								 */

								currentComponent = "tUniqRow_2";

								/**
								 * [tUniqRow_2 process_data_end ] stop
								 */

							} // End of branch "DimUser"

							/**
							 * [tMap_6 process_data_end ] start
							 */

							currentComponent = "tMap_6";

							/**
							 * [tMap_6 process_data_end ] stop
							 */

						} // End of branch "row8"

						/**
						 * [tFileInputDelimited_3 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_3";

						/**
						 * [tFileInputDelimited_3 process_data_end ] stop
						 */

						if (!isChildJob && (Boolean) globalMap.get("ENABLE_TRACES_CONNECTION_tFileInputDelimited_3")) {
							if (globalMap.get("USE_CONDITION") != null && (Boolean) globalMap.get("USE_CONDITION")) {
								if (globalMap.get("TRACE_CONDITION") != null
										&& (Boolean) globalMap.get("TRACE_CONDITION")) {
									// if next breakpoint has been clicked on UI or if start job, should wait action
									// of user.
									if (runTrace.isNextBreakpoint()) {
										runTrace.waitForUserAction();
									} else if (runTrace.isNextRow()) {
										runTrace.waitForUserAction();
									}
								} else {
									// if next row has been clicked on UI or if start job, should wait action of
									// user.
									if (runTrace.isNextRow()) {
										runTrace.waitForUserAction();
									}
								}
							} else { // no condition set
								if (runTrace.isNextRow()) {
									runTrace.waitForUserAction();
								} else {
									Thread.sleep(1000);
								}
							}

						}
						globalMap.put("USE_CONDITION", Boolean.FALSE);

						/**
						 * [tFileInputDelimited_3 end ] start
						 */

						currentComponent = "tFileInputDelimited_3";

						nb_line_tFileInputDelimited_3++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_3 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_3 != null) {
							csvReadertFileInputDelimited_3.close();
						}
					}
					if (csvReadertFileInputDelimited_3 != null) {
						globalMap.put("tFileInputDelimited_3_NB_LINE", nb_line_tFileInputDelimited_3);
					}

				}

				ok_Hash.put("tFileInputDelimited_3", true);
				end_Hash.put("tFileInputDelimited_3", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_3 end ] stop
				 */

				/**
				 * [tMap_6 end ] start
				 */

				currentComponent = "tMap_6";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row8");
				}

				ok_Hash.put("tMap_6", true);
				end_Hash.put("tMap_6", System.currentTimeMillis());

				/**
				 * [tMap_6 end ] stop
				 */

				/**
				 * [tUniqRow_2 end ] start
				 */

				currentComponent = "tUniqRow_2";

				globalMap.put("tUniqRow_2_NB_UNIQUES", nb_uniques_tUniqRow_2);
				globalMap.put("tUniqRow_2_NB_DUPLICATES", nb_duplicates_tUniqRow_2);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "DimUser");
				}

				ok_Hash.put("tUniqRow_2", true);
				end_Hash.put("tUniqRow_2", System.currentTimeMillis());

				/**
				 * [tUniqRow_2 end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					int countSum_tDBOutput_2 = 0;
					if (pstmt_tDBOutput_2 != null && batchSizeCounter_tDBOutput_2 > 0) {

						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							if (countEach_tDBOutput_2 == -2 || countEach_tDBOutput_2 == -3) {
								break;
							}
							countSum_tDBOutput_2 += countEach_tDBOutput_2;
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					}

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

				} catch (java.sql.BatchUpdateException e) {
					globalMap.put("tDBOutput_2_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					insertedCount_tDBOutput_2 += countSum_tDBOutput_2;

					System.err.println(e.getMessage());

				}
				if (pstmt_tDBOutput_2 != null) {

					pstmt_tDBOutput_2.close();
					resourceMap.remove("pstmt_tDBOutput_2");

				}
				resourceMap.put("statementClosed_tDBOutput_2", true);
				if (rowsToCommitCount_tDBOutput_2 != 0) {

				}
				conn_tDBOutput_2.commit();
				if (rowsToCommitCount_tDBOutput_2 != 0) {

					rowsToCommitCount_tDBOutput_2 = 0;
				}
				commitCounter_tDBOutput_2 = 0;
				conn_tDBOutput_2.close();
				resourceMap.put("finish_tDBOutput_2", true);

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row9");
				}

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_3 finally ] start
				 */

				currentComponent = "tFileInputDelimited_3";

				/**
				 * [tFileInputDelimited_3 finally ] stop
				 */

				/**
				 * [tMap_6 finally ] start
				 */

				currentComponent = "tMap_6";

				/**
				 * [tMap_6 finally ] stop
				 */

				/**
				 * [tUniqRow_2 finally ] start
				 */

				currentComponent = "tUniqRow_2";

				/**
				 * [tUniqRow_2 finally ] stop
				 */

				/**
				 * [tDBOutput_2 finally ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
						if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_2")) != null) {
							pstmtToClose_tDBOutput_2.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_2") == null) {
						java.sql.Connection ctn_tDBOutput_2 = null;
						if ((ctn_tDBOutput_2 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_2")) != null) {
							try {
								ctn_tDBOutput_2.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_2) {
								String errorMessage_tDBOutput_2 = "failed to close the connection in tDBOutput_2 :"
										+ sqlEx_tDBOutput_2.getMessage();
								System.err.println(errorMessage_tDBOutput_2);
							}
						}
					}
				}

				/**
				 * [tDBOutput_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_3_SUBPROCESS_STATE", 1);
	}

	public static class row14Struct implements routines.system.IPersistableRow<row14Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer id;

		public Integer getId() {
			return this.id;
		}

		public Integer rating_Id;

		public Integer getRating_Id() {
			return this.rating_Id;
		}

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		public Integer DateKey;

		public Integer getDateKey() {
			return this.DateKey;
		}

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public Float rating;

		public Float getRating() {
			return this.rating;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.id = readInteger(dis);

					this.rating_Id = readInteger(dis);

					this.userId = readInteger(dis);

					this.DateKey = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.id = readInteger(dis);

					this.rating_Id = readInteger(dis);

					this.userId = readInteger(dis);

					this.DateKey = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id, dos);

				// Integer

				writeInteger(this.rating_Id, dos);

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.DateKey, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id, dos);

				// Integer

				writeInteger(this.rating_Id, dos);

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.DateKey, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + String.valueOf(id));
			sb.append(",rating_Id=" + String.valueOf(rating_Id));
			sb.append(",userId=" + String.valueOf(userId));
			sb.append(",DateKey=" + String.valueOf(DateKey));
			sb.append(",movieId=" + String.valueOf(movieId));
			sb.append(",rating=" + String.valueOf(rating));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row14Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class copyOfout1Struct implements routines.system.IPersistableRow<copyOfout1Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer id;

		public Integer getId() {
			return this.id;
		}

		public Integer rating_Id;

		public Integer getRating_Id() {
			return this.rating_Id;
		}

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		public Integer DateKey;

		public Integer getDateKey() {
			return this.DateKey;
		}

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public Float rating;

		public Float getRating() {
			return this.rating;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.id = readInteger(dis);

					this.rating_Id = readInteger(dis);

					this.userId = readInteger(dis);

					this.DateKey = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.id = readInteger(dis);

					this.rating_Id = readInteger(dis);

					this.userId = readInteger(dis);

					this.DateKey = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id, dos);

				// Integer

				writeInteger(this.rating_Id, dos);

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.DateKey, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id, dos);

				// Integer

				writeInteger(this.rating_Id, dos);

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.DateKey, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + String.valueOf(id));
			sb.append(",rating_Id=" + String.valueOf(rating_Id));
			sb.append(",userId=" + String.valueOf(userId));
			sb.append(",DateKey=" + String.valueOf(DateKey));
			sb.append(",movieId=" + String.valueOf(movieId));
			sb.append(",rating=" + String.valueOf(rating));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(copyOfout1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class copyOfout2Struct implements routines.system.IPersistableRow<copyOfout2Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer id;

		public Integer getId() {
			return this.id;
		}

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public Float rating;

		public Float getRating() {
			return this.rating;
		}

		public java.util.Date timestamp;

		public java.util.Date getTimestamp() {
			return this.timestamp;
		}

		public Integer datekey;

		public Integer getDatekey() {
			return this.datekey;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.id = readInteger(dis);

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

					this.timestamp = readDate(dis);

					this.datekey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.id = readInteger(dis);

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

					this.timestamp = readDate(dis);

					this.datekey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.id, dos);

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

				// java.util.Date

				writeDate(this.timestamp, dos);

				// Integer

				writeInteger(this.datekey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.id, dos);

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

				// java.util.Date

				writeDate(this.timestamp, dos);

				// Integer

				writeInteger(this.datekey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + String.valueOf(id));
			sb.append(",userId=" + String.valueOf(userId));
			sb.append(",movieId=" + String.valueOf(movieId));
			sb.append(",rating=" + String.valueOf(rating));
			sb.append(",timestamp=" + String.valueOf(timestamp));
			sb.append(",datekey=" + String.valueOf(datekey));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(copyOfout2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row10Struct implements routines.system.IPersistableRow<row10Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public Float rating;

		public Float getRating() {
			return this.rating;
		}

		public java.util.Date timestamp;

		public java.util.Date getTimestamp() {
			return this.timestamp;
		}

		public Integer datekey;

		public Integer getDatekey() {
			return this.datekey;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

					this.timestamp = readDate(dis);

					this.datekey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

					this.timestamp = readDate(dis);

					this.datekey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

				// java.util.Date

				writeDate(this.timestamp, dos);

				// Integer

				writeInteger(this.datekey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

				// java.util.Date

				writeDate(this.timestamp, dos);

				// Integer

				writeInteger(this.datekey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("userId=" + String.valueOf(userId));
			sb.append(",movieId=" + String.valueOf(movieId));
			sb.append(",rating=" + String.valueOf(rating));
			sb.append(",timestamp=" + String.valueOf(timestamp));
			sb.append(",datekey=" + String.valueOf(datekey));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row10Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class after_tFileInputDelimited_4Struct
			implements routines.system.IPersistableRow<after_tFileInputDelimited_4Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public Float rating;

		public Float getRating() {
			return this.rating;
		}

		public java.util.Date timestamp;

		public java.util.Date getTimestamp() {
			return this.timestamp;
		}

		public Integer datekey;

		public Integer getDatekey() {
			return this.datekey;
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

					this.timestamp = readDate(dis);

					this.datekey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

					this.movieId = readInteger(dis);

					length = dis.readByte();
					if (length == -1) {
						this.rating = null;
					} else {
						this.rating = dis.readFloat();
					}

					this.timestamp = readDate(dis);

					this.datekey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

				// java.util.Date

				writeDate(this.timestamp, dos);

				// Integer

				writeInteger(this.datekey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

				// Integer

				writeInteger(this.movieId, dos);

				// Float

				if (this.rating == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeFloat(this.rating);
				}

				// java.util.Date

				writeDate(this.timestamp, dos);

				// Integer

				writeInteger(this.datekey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("userId=" + String.valueOf(userId));
			sb.append(",movieId=" + String.valueOf(movieId));
			sb.append(",rating=" + String.valueOf(rating));
			sb.append(",timestamp=" + String.valueOf(timestamp));
			sb.append(",datekey=" + String.valueOf(datekey));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tFileInputDelimited_4Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				tFileInputDelimited_5Process(globalMap);
				tFileInputDelimited_6Process(globalMap);
				tFileInputDelimited_7Process(globalMap);

				row10Struct row10 = new row10Struct();
				copyOfout2Struct copyOfout2 = new copyOfout2Struct();
				copyOfout1Struct copyOfout1 = new copyOfout1Struct();
				copyOfout1Struct row14 = copyOfout1;

				/**
				 * [tDBOutput_3 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tDBOutput_3", false);
				start_Hash.put("tDBOutput_3", System.currentTimeMillis());

				currentComponent = "tDBOutput_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row14");
				}

				int tos_count_tDBOutput_3 = 0;

				int nb_line_tDBOutput_3 = 0;
				int nb_line_update_tDBOutput_3 = 0;
				int nb_line_inserted_tDBOutput_3 = 0;
				int nb_line_deleted_tDBOutput_3 = 0;
				int nb_line_rejected_tDBOutput_3 = 0;

				int deletedCount_tDBOutput_3 = 0;
				int updatedCount_tDBOutput_3 = 0;
				int insertedCount_tDBOutput_3 = 0;
				int rowsToCommitCount_tDBOutput_3 = 0;
				int rejectedCount_tDBOutput_3 = 0;
				String dbschema_tDBOutput_3 = null;
				String tableName_tDBOutput_3 = null;
				boolean whetherReject_tDBOutput_3 = false;

				java.util.Calendar calendar_tDBOutput_3 = java.util.Calendar.getInstance();
				long year1_tDBOutput_3 = TalendDate.parseDate("yyyy-MM-dd", "0001-01-01").getTime();
				long year2_tDBOutput_3 = TalendDate.parseDate("yyyy-MM-dd", "1753-01-01").getTime();
				long year10000_tDBOutput_3 = TalendDate.parseDate("yyyy-MM-dd HH:mm:ss", "9999-12-31 24:00:00")
						.getTime();
				long date_tDBOutput_3;

				java.util.Calendar calendar_datetimeoffset_tDBOutput_3 = java.util.Calendar
						.getInstance(java.util.TimeZone.getTimeZone("UTC"));

				java.sql.Connection conn_tDBOutput_3 = null;
				String dbUser_tDBOutput_3 = null;
				dbschema_tDBOutput_3 = "dbo";
				String driverClass_tDBOutput_3 = "net.sourceforge.jtds.jdbc.Driver";

				java.lang.Class.forName(driverClass_tDBOutput_3);
				String port_tDBOutput_3 = "1433";
				String dbname_tDBOutput_3 = "rabii";
				String url_tDBOutput_3 = "jdbc:jtds:sqlserver://" + "localhost";
				if (!"".equals(port_tDBOutput_3)) {
					url_tDBOutput_3 += ":" + "1433";
				}
				if (!"".equals(dbname_tDBOutput_3)) {
					url_tDBOutput_3 += "//" + "rabii";

				}
				url_tDBOutput_3 += ";appName=" + projectName + ";" + "instance=SQLEXPRESS";
				dbUser_tDBOutput_3 = "sa";

				final String decryptedPassword_tDBOutput_3 = routines.system.PasswordEncryptUtil.decryptPassword(
						"enc:routine.encryption.key.v1:Xe4v0zssSgN5D3flqdK8PTUOYBjX3gz6FBo2fI6Ay2nsO+4=");

				String dbPwd_tDBOutput_3 = decryptedPassword_tDBOutput_3;
				conn_tDBOutput_3 = java.sql.DriverManager.getConnection(url_tDBOutput_3, dbUser_tDBOutput_3,
						dbPwd_tDBOutput_3);

				resourceMap.put("conn_tDBOutput_3", conn_tDBOutput_3);

				conn_tDBOutput_3.setAutoCommit(false);
				int commitEvery_tDBOutput_3 = 10000;
				int commitCounter_tDBOutput_3 = 0;

				int batchSize_tDBOutput_3 = 10000;
				int batchSizeCounter_tDBOutput_3 = 0;

				if (dbschema_tDBOutput_3 == null || dbschema_tDBOutput_3.trim().length() == 0) {
					tableName_tDBOutput_3 = "dimensions";
				} else {
					tableName_tDBOutput_3 = dbschema_tDBOutput_3 + "].[" + "dimensions";
				}
				int count_tDBOutput_3 = 0;

				boolean whetherExist_tDBOutput_3 = false;
				try (java.sql.Statement isExistStmt_tDBOutput_3 = conn_tDBOutput_3.createStatement()) {
					try {
						isExistStmt_tDBOutput_3.execute("SELECT TOP 1 1 FROM [" + tableName_tDBOutput_3 + "]");
						whetherExist_tDBOutput_3 = true;
					} catch (java.lang.Exception e) {
						globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());
						whetherExist_tDBOutput_3 = false;
					}
				}
				if (!whetherExist_tDBOutput_3) {
					try (java.sql.Statement stmtCreate_tDBOutput_3 = conn_tDBOutput_3.createStatement()) {
						stmtCreate_tDBOutput_3.execute("CREATE TABLE [" + tableName_tDBOutput_3
								+ "]([id] INT ,[rating_Id] INT ,[userId] INT ,[DateKey] INT ,[movieId] INT ,[rating] REAL )");
					}
				}
				String insert_tDBOutput_3 = "INSERT INTO [" + tableName_tDBOutput_3
						+ "] ([id],[rating_Id],[userId],[DateKey],[movieId],[rating]) VALUES (?,?,?,?,?,?)";
				java.sql.PreparedStatement pstmt_tDBOutput_3 = conn_tDBOutput_3.prepareStatement(insert_tDBOutput_3);
				resourceMap.put("pstmt_tDBOutput_3", pstmt_tDBOutput_3);

				/**
				 * [tDBOutput_3 begin ] stop
				 */

				/**
				 * [tFlowMeter_17 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tFlowMeter_17", false);
				start_Hash.put("tFlowMeter_17", System.currentTimeMillis());

				currentComponent = "tFlowMeter_17";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "copyOfout1");
				}

				int tos_count_tFlowMeter_17 = 0;

				int count_tFlowMeter_17 = 0;

				/**
				 * [tFlowMeter_17 begin ] stop
				 */

				/**
				 * [tMap_8 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tMap_8", false);
				start_Hash.put("tMap_8", System.currentTimeMillis());

				currentComponent = "tMap_8";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "copyOfout2");
				}

				int tos_count_tMap_8 = 0;

// ###############################
// # Lookup's keys initialization

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct> tHash_Lookup_row11 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct>) globalMap
						.get("tHash_Lookup_row11"));

				row11Struct row11HashKey = new row11Struct();
				row11Struct row11Default = new row11Struct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row13Struct> tHash_Lookup_row13 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row13Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row13Struct>) globalMap
						.get("tHash_Lookup_row13"));

				row13Struct row13HashKey = new row13Struct();
				row13Struct row13Default = new row13Struct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct> tHash_Lookup_row12 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct>) globalMap
						.get("tHash_Lookup_row12"));

				row12Struct row12HashKey = new row12Struct();
				row12Struct row12Default = new row12Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_8__Struct {
				}
				Var__tMap_8__Struct Var__tMap_8 = new Var__tMap_8__Struct();
// ###############################

// ###############################
// # Outputs initialization
				copyOfout1Struct copyOfout1_tmp = new copyOfout1Struct();
// ###############################

				/**
				 * [tMap_8 begin ] stop
				 */

				/**
				 * [tMap_7 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tMap_7", false);
				start_Hash.put("tMap_7", System.currentTimeMillis());

				currentComponent = "tMap_7";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row10");
				}

				int tos_count_tMap_7 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_7__Struct {
				}
				Var__tMap_7__Struct Var__tMap_7 = new Var__tMap_7__Struct();
// ###############################

// ###############################
// # Outputs initialization
				copyOfout2Struct copyOfout2_tmp = new copyOfout2Struct();
// ###############################

				/**
				 * [tMap_7 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_4 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tFileInputDelimited_4", false);
				start_Hash.put("tFileInputDelimited_4", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_4";

				int tos_count_tFileInputDelimited_4 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_4 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_4 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_4 = null;
				int limit_tFileInputDelimited_4 = -1;
				try {

					Object filename_tFileInputDelimited_4 = "C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/Cleaned_rating.csv";
					if (filename_tFileInputDelimited_4 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_4 = 0, random_value_tFileInputDelimited_4 = -1;
						if (footer_value_tFileInputDelimited_4 > 0 || random_value_tFileInputDelimited_4 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_4 = new org.talend.fileprocess.FileInputDelimited(
								"C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/Cleaned_rating.csv",
								"US-ASCII", ",", "\n", false, 1, 0, limit_tFileInputDelimited_4, -1, false);
					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE", e.getMessage());

						throw e;

					}

					while (fid_tFileInputDelimited_4 != null && fid_tFileInputDelimited_4.nextRecord()) {
						rowstate_tFileInputDelimited_4.reset();

						row10 = null;

						boolean whetherReject_tFileInputDelimited_4 = false;
						row10 = new row10Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_4 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_4 = 0;

							temp = fid_tFileInputDelimited_4.get(columnIndexWithD_tFileInputDelimited_4);
							if (temp.length() > 0) {

								try {

									row10.userId = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_4) {
									globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
											ex_tFileInputDelimited_4.getMessage());
									rowstate_tFileInputDelimited_4.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"userId", "row10", temp, ex_tFileInputDelimited_4),
											ex_tFileInputDelimited_4));
								}

							} else {

								row10.userId = null;

							}

							columnIndexWithD_tFileInputDelimited_4 = 1;

							temp = fid_tFileInputDelimited_4.get(columnIndexWithD_tFileInputDelimited_4);
							if (temp.length() > 0) {

								try {

									row10.movieId = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_4) {
									globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
											ex_tFileInputDelimited_4.getMessage());
									rowstate_tFileInputDelimited_4.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"movieId", "row10", temp, ex_tFileInputDelimited_4),
											ex_tFileInputDelimited_4));
								}

							} else {

								row10.movieId = null;

							}

							columnIndexWithD_tFileInputDelimited_4 = 2;

							temp = fid_tFileInputDelimited_4.get(columnIndexWithD_tFileInputDelimited_4);
							if (temp.length() > 0) {

								try {

									row10.rating = ParserUtils.parseTo_Float(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_4) {
									globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
											ex_tFileInputDelimited_4.getMessage());
									rowstate_tFileInputDelimited_4.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"rating", "row10", temp, ex_tFileInputDelimited_4),
											ex_tFileInputDelimited_4));
								}

							} else {

								row10.rating = null;

							}

							columnIndexWithD_tFileInputDelimited_4 = 3;

							temp = fid_tFileInputDelimited_4.get(columnIndexWithD_tFileInputDelimited_4);
							if (temp.length() > 0) {

								try {

									row10.timestamp = ParserUtils.parseTo_Date(temp, "dd-MM-yyyy");

								} catch (java.lang.Exception ex_tFileInputDelimited_4) {
									globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
											ex_tFileInputDelimited_4.getMessage());
									rowstate_tFileInputDelimited_4.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"timestamp", "row10", temp, ex_tFileInputDelimited_4),
											ex_tFileInputDelimited_4));
								}

							} else {

								row10.timestamp = null;

							}

							columnIndexWithD_tFileInputDelimited_4 = 4;

							temp = fid_tFileInputDelimited_4.get(columnIndexWithD_tFileInputDelimited_4);
							if (temp.length() > 0) {

								try {

									row10.datekey = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_4) {
									globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE",
											ex_tFileInputDelimited_4.getMessage());
									rowstate_tFileInputDelimited_4.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"datekey", "row10", temp, ex_tFileInputDelimited_4),
											ex_tFileInputDelimited_4));
								}

							} else {

								row10.datekey = null;

							}

							if (rowstate_tFileInputDelimited_4.getException() != null) {
								throw rowstate_tFileInputDelimited_4.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_4_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_4 = true;

							throw (e);

						}

						/**
						 * [tFileInputDelimited_4 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_4 main ] start
						 */

						currentComponent = "tFileInputDelimited_4";

						if (row10 != null) {
							globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.TRUE);
							if (runTrace.isPause()) {
								while (runTrace.isPause()) {
									Thread.sleep(100);
								}
							} else {

								// here we dump the line content for trace purpose
								java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

								runTraceData.put("userId", String.valueOf(row10.userId));

								runTraceData.put("movieId", String.valueOf(row10.movieId));

								runTraceData.put("rating", String.valueOf(row10.rating));

								runTraceData.put("timestamp", String.valueOf(row10.timestamp));

								runTraceData.put("datekey", String.valueOf(row10.datekey));

								runTrace.sendTrace("row10", "tFileInputDelimited_4", runTraceData);
							}

						}

						tos_count_tFileInputDelimited_4++;

						/**
						 * [tFileInputDelimited_4 main ] stop
						 */

						/**
						 * [tFileInputDelimited_4 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_4";

						/**
						 * [tFileInputDelimited_4 process_data_begin ] stop
						 */
// Start of branch "row10"
						if (row10 != null) {

							/**
							 * [tMap_7 main ] start
							 */

							currentComponent = "tMap_7";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row10"

								);
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_7 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_7 = false;
							boolean mainRowRejected_tMap_7 = false;

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_7__Struct Var = Var__tMap_7;// ###############################
								// ###############################
								// # Output tables

								copyOfout2 = null;

// # Output table : 'copyOfout2'
								copyOfout2_tmp.id = Numeric.sequence("seq", 1, 1);
								copyOfout2_tmp.userId = row10.userId;
								copyOfout2_tmp.movieId = row10.movieId;
								copyOfout2_tmp.rating = row10.rating;
								copyOfout2_tmp.timestamp = row10.timestamp;
								copyOfout2_tmp.datekey = row10.datekey;
								copyOfout2 = copyOfout2_tmp;
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_7 = false;

							if (copyOfout2 != null) {
								globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.TRUE);
								if (runTrace.isPause()) {
									while (runTrace.isPause()) {
										Thread.sleep(100);
									}
								} else {

									// here we dump the line content for trace purpose
									java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

									runTraceData.put("id", String.valueOf(copyOfout2.id));

									runTraceData.put("userId", String.valueOf(copyOfout2.userId));

									runTraceData.put("movieId", String.valueOf(copyOfout2.movieId));

									runTraceData.put("rating", String.valueOf(copyOfout2.rating));

									runTraceData.put("timestamp", String.valueOf(copyOfout2.timestamp));

									runTraceData.put("datekey", String.valueOf(copyOfout2.datekey));

									runTrace.sendTrace("copyOfout2", "tFileInputDelimited_4", runTraceData);
								}

							}

							tos_count_tMap_7++;

							/**
							 * [tMap_7 main ] stop
							 */

							/**
							 * [tMap_7 process_data_begin ] start
							 */

							currentComponent = "tMap_7";

							/**
							 * [tMap_7 process_data_begin ] stop
							 */
// Start of branch "copyOfout2"
							if (copyOfout2 != null) {

								/**
								 * [tMap_8 main ] start
								 */

								currentComponent = "tMap_8";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "copyOfout2"

									);
								}

								boolean hasCasePrimitiveKeyWithNull_tMap_8 = false;

								// ###############################
								// # Input tables (lookups)
								boolean rejectedInnerJoin_tMap_8 = false;
								boolean mainRowRejected_tMap_8 = false;

								///////////////////////////////////////////////
								// Starting Lookup Table "row11"
								///////////////////////////////////////////////

								boolean forceLooprow11 = false;

								row11Struct row11ObjectFromLookup = null;

								if (!rejectedInnerJoin_tMap_8) { // G_TM_M_020

									hasCasePrimitiveKeyWithNull_tMap_8 = false;

									row11HashKey.userId = row10.userId;

									row11HashKey.hashCodeDirty = true;

									tHash_Lookup_row11.lookup(row11HashKey);

								} // G_TM_M_020

								if (tHash_Lookup_row11 != null && tHash_Lookup_row11.getCount(row11HashKey) > 1) { // G
																													// 071

									// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
									// 'row11' and it contains more one result from keys : row11.userId = '" +
									// row11HashKey.userId + "'");
								} // G 071

								row11Struct row11 = null;

								row11Struct fromLookup_row11 = null;
								row11 = row11Default;

								if (tHash_Lookup_row11 != null && tHash_Lookup_row11.hasNext()) { // G 099

									fromLookup_row11 = tHash_Lookup_row11.next();

								} // G 099

								if (fromLookup_row11 != null) {
									row11 = fromLookup_row11;
								}

								///////////////////////////////////////////////
								// Starting Lookup Table "row13"
								///////////////////////////////////////////////

								boolean forceLooprow13 = false;

								row13Struct row13ObjectFromLookup = null;

								if (!rejectedInnerJoin_tMap_8) { // G_TM_M_020

									hasCasePrimitiveKeyWithNull_tMap_8 = false;

									row13HashKey.DateKey = row10.datekey;

									row13HashKey.hashCodeDirty = true;

									tHash_Lookup_row13.lookup(row13HashKey);

								} // G_TM_M_020

								if (tHash_Lookup_row13 != null && tHash_Lookup_row13.getCount(row13HashKey) > 1) { // G
																													// 071

									// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
									// 'row13' and it contains more one result from keys : row13.DateKey = '" +
									// row13HashKey.DateKey + "'");
								} // G 071

								row13Struct row13 = null;

								row13Struct fromLookup_row13 = null;
								row13 = row13Default;

								if (tHash_Lookup_row13 != null && tHash_Lookup_row13.hasNext()) { // G 099

									fromLookup_row13 = tHash_Lookup_row13.next();

								} // G 099

								if (fromLookup_row13 != null) {
									row13 = fromLookup_row13;
								}

								///////////////////////////////////////////////
								// Starting Lookup Table "row12"
								///////////////////////////////////////////////

								boolean forceLooprow12 = false;

								row12Struct row12ObjectFromLookup = null;

								if (!rejectedInnerJoin_tMap_8) { // G_TM_M_020

									hasCasePrimitiveKeyWithNull_tMap_8 = false;

									row12HashKey.movieId = row10.movieId;

									row12HashKey.hashCodeDirty = true;

									tHash_Lookup_row12.lookup(row12HashKey);

								} // G_TM_M_020

								if (tHash_Lookup_row12 != null && tHash_Lookup_row12.getCount(row12HashKey) > 1) { // G
																													// 071

									// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup
									// 'row12' and it contains more one result from keys : row12.movieId = '" +
									// row12HashKey.movieId + "'");
								} // G 071

								row12Struct row12 = null;

								row12Struct fromLookup_row12 = null;
								row12 = row12Default;

								if (tHash_Lookup_row12 != null && tHash_Lookup_row12.hasNext()) { // G 099

									fromLookup_row12 = tHash_Lookup_row12.next();

								} // G 099

								if (fromLookup_row12 != null) {
									row12 = fromLookup_row12;
								}

								// ###############################
								{ // start of Var scope

									// ###############################
									// # Vars tables

									Var__tMap_8__Struct Var = Var__tMap_8;// ###############################
									// ###############################
									// # Output tables

									copyOfout1 = null;

// # Output table : 'copyOfout1'
									copyOfout1_tmp.id = Numeric.sequence("seq", 1, 1);
									copyOfout1_tmp.rating_Id = copyOfout2.id;
									copyOfout1_tmp.userId = row11.userId;
									copyOfout1_tmp.DateKey = row13.DateKey;
									copyOfout1_tmp.movieId = row12.movieId;
									copyOfout1_tmp.rating = copyOfout2.rating;
									copyOfout1 = copyOfout1_tmp;
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_8 = false;

								if (copyOfout1 != null) {
									globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.TRUE);
									if (runTrace.isPause()) {
										while (runTrace.isPause()) {
											Thread.sleep(100);
										}
									} else {

										// here we dump the line content for trace purpose
										java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

										runTraceData.put("id", String.valueOf(copyOfout1.id));

										runTraceData.put("rating_Id", String.valueOf(copyOfout1.rating_Id));

										runTraceData.put("userId", String.valueOf(copyOfout1.userId));

										runTraceData.put("DateKey", String.valueOf(copyOfout1.DateKey));

										runTraceData.put("movieId", String.valueOf(copyOfout1.movieId));

										runTraceData.put("rating", String.valueOf(copyOfout1.rating));

										runTrace.sendTrace("copyOfout1", "tFileInputDelimited_4", runTraceData);
									}

								}

								tos_count_tMap_8++;

								/**
								 * [tMap_8 main ] stop
								 */

								/**
								 * [tMap_8 process_data_begin ] start
								 */

								currentComponent = "tMap_8";

								/**
								 * [tMap_8 process_data_begin ] stop
								 */
// Start of branch "copyOfout1"
								if (copyOfout1 != null) {

									/**
									 * [tFlowMeter_17 main ] start
									 */

									currentComponent = "tFlowMeter_17";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "copyOfout1"

										);
									}

									count_tFlowMeter_17++;

									row14 = copyOfout1;

									if (row14 != null) {
										globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.TRUE);
										if (runTrace.isPause()) {
											while (runTrace.isPause()) {
												Thread.sleep(100);
											}
										} else {

											// here we dump the line content for trace purpose
											java.util.LinkedHashMap<String, String> runTraceData = new java.util.LinkedHashMap<String, String>();

											runTraceData.put("id", String.valueOf(row14.id));

											runTraceData.put("rating_Id", String.valueOf(row14.rating_Id));

											runTraceData.put("userId", String.valueOf(row14.userId));

											runTraceData.put("DateKey", String.valueOf(row14.DateKey));

											runTraceData.put("movieId", String.valueOf(row14.movieId));

											runTraceData.put("rating", String.valueOf(row14.rating));

											runTrace.sendTrace("row14", "tFileInputDelimited_4", runTraceData);
										}

									}

									tos_count_tFlowMeter_17++;

									/**
									 * [tFlowMeter_17 main ] stop
									 */

									/**
									 * [tFlowMeter_17 process_data_begin ] start
									 */

									currentComponent = "tFlowMeter_17";

									/**
									 * [tFlowMeter_17 process_data_begin ] stop
									 */

									/**
									 * [tDBOutput_3 main ] start
									 */

									currentComponent = "tDBOutput_3";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "row14"

										);
									}

									whetherReject_tDBOutput_3 = false;
									if (row14.id == null) {
										pstmt_tDBOutput_3.setNull(1, java.sql.Types.INTEGER);
									} else {
										pstmt_tDBOutput_3.setInt(1, row14.id);
									}

									if (row14.rating_Id == null) {
										pstmt_tDBOutput_3.setNull(2, java.sql.Types.INTEGER);
									} else {
										pstmt_tDBOutput_3.setInt(2, row14.rating_Id);
									}

									if (row14.userId == null) {
										pstmt_tDBOutput_3.setNull(3, java.sql.Types.INTEGER);
									} else {
										pstmt_tDBOutput_3.setInt(3, row14.userId);
									}

									if (row14.DateKey == null) {
										pstmt_tDBOutput_3.setNull(4, java.sql.Types.INTEGER);
									} else {
										pstmt_tDBOutput_3.setInt(4, row14.DateKey);
									}

									if (row14.movieId == null) {
										pstmt_tDBOutput_3.setNull(5, java.sql.Types.INTEGER);
									} else {
										pstmt_tDBOutput_3.setInt(5, row14.movieId);
									}

									if (row14.rating == null) {
										pstmt_tDBOutput_3.setNull(6, java.sql.Types.FLOAT);
									} else {
										pstmt_tDBOutput_3.setFloat(6, row14.rating);
									}

									pstmt_tDBOutput_3.addBatch();
									nb_line_tDBOutput_3++;

									batchSizeCounter_tDBOutput_3++;

									////////// batch execute by batch size///////
									class LimitBytesHelper_tDBOutput_3 {
										public int limitBytePart1(int counter,
												java.sql.PreparedStatement pstmt_tDBOutput_3) throws Exception {
											try {

												for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
													if (countEach_tDBOutput_3 == -2 || countEach_tDBOutput_3 == -3) {
														break;
													}
													counter += countEach_tDBOutput_3;
												}

											} catch (java.sql.BatchUpdateException e) {
												globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());

												int countSum_tDBOutput_3 = 0;
												for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
													counter += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
												}

												System.err.println(e.getMessage());

											}
											return counter;
										}

										public int limitBytePart2(int counter,
												java.sql.PreparedStatement pstmt_tDBOutput_3) throws Exception {
											try {

												for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
													if (countEach_tDBOutput_3 == -2 || countEach_tDBOutput_3 == -3) {
														break;
													}
													counter += countEach_tDBOutput_3;
												}

											} catch (java.sql.BatchUpdateException e) {
												globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());

												for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
													counter += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
												}

												System.err.println(e.getMessage());

											}
											return counter;
										}
									}
									if ((batchSize_tDBOutput_3 > 0)
											&& (batchSize_tDBOutput_3 <= batchSizeCounter_tDBOutput_3)) {

										insertedCount_tDBOutput_3 = new LimitBytesHelper_tDBOutput_3()
												.limitBytePart1(insertedCount_tDBOutput_3, pstmt_tDBOutput_3);
										rowsToCommitCount_tDBOutput_3 = insertedCount_tDBOutput_3;

										batchSizeCounter_tDBOutput_3 = 0;
									}

									//////////// commit every////////////

									commitCounter_tDBOutput_3++;
									if (commitEvery_tDBOutput_3 <= commitCounter_tDBOutput_3) {
										if ((batchSize_tDBOutput_3 > 0) && (batchSizeCounter_tDBOutput_3 > 0)) {

											insertedCount_tDBOutput_3 = new LimitBytesHelper_tDBOutput_3()
													.limitBytePart1(insertedCount_tDBOutput_3, pstmt_tDBOutput_3);

											batchSizeCounter_tDBOutput_3 = 0;
										}
										if (rowsToCommitCount_tDBOutput_3 != 0) {

										}
										conn_tDBOutput_3.commit();
										if (rowsToCommitCount_tDBOutput_3 != 0) {

											rowsToCommitCount_tDBOutput_3 = 0;
										}
										commitCounter_tDBOutput_3 = 0;
									}

									tos_count_tDBOutput_3++;

									/**
									 * [tDBOutput_3 main ] stop
									 */

									/**
									 * [tDBOutput_3 process_data_begin ] start
									 */

									currentComponent = "tDBOutput_3";

									/**
									 * [tDBOutput_3 process_data_begin ] stop
									 */

									/**
									 * [tDBOutput_3 process_data_end ] start
									 */

									currentComponent = "tDBOutput_3";

									/**
									 * [tDBOutput_3 process_data_end ] stop
									 */

									/**
									 * [tFlowMeter_17 process_data_end ] start
									 */

									currentComponent = "tFlowMeter_17";

									/**
									 * [tFlowMeter_17 process_data_end ] stop
									 */

								} // End of branch "copyOfout1"

								/**
								 * [tMap_8 process_data_end ] start
								 */

								currentComponent = "tMap_8";

								/**
								 * [tMap_8 process_data_end ] stop
								 */

							} // End of branch "copyOfout2"

							/**
							 * [tMap_7 process_data_end ] start
							 */

							currentComponent = "tMap_7";

							/**
							 * [tMap_7 process_data_end ] stop
							 */

						} // End of branch "row10"

						/**
						 * [tFileInputDelimited_4 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_4";

						/**
						 * [tFileInputDelimited_4 process_data_end ] stop
						 */

						if (!isChildJob && (Boolean) globalMap.get("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4")) {
							if (globalMap.get("USE_CONDITION") != null && (Boolean) globalMap.get("USE_CONDITION")) {
								if (globalMap.get("TRACE_CONDITION") != null
										&& (Boolean) globalMap.get("TRACE_CONDITION")) {
									// if next breakpoint has been clicked on UI or if start job, should wait action
									// of user.
									if (runTrace.isNextBreakpoint()) {
										runTrace.waitForUserAction();
									} else if (runTrace.isNextRow()) {
										runTrace.waitForUserAction();
									}
								} else {
									// if next row has been clicked on UI or if start job, should wait action of
									// user.
									if (runTrace.isNextRow()) {
										runTrace.waitForUserAction();
									}
								}
							} else { // no condition set
								if (runTrace.isNextRow()) {
									runTrace.waitForUserAction();
								} else {
									Thread.sleep(1000);
								}
							}

						}
						globalMap.put("USE_CONDITION", Boolean.FALSE);

						/**
						 * [tFileInputDelimited_4 end ] start
						 */

						currentComponent = "tFileInputDelimited_4";

					}
				} finally {
					if (!((Object) ("C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/Cleaned_rating.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_4 != null) {
							fid_tFileInputDelimited_4.close();
						}
					}
					if (fid_tFileInputDelimited_4 != null) {
						globalMap.put("tFileInputDelimited_4_NB_LINE", fid_tFileInputDelimited_4.getRowNumber());

					}
				}

				ok_Hash.put("tFileInputDelimited_4", true);
				end_Hash.put("tFileInputDelimited_4", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_4 end ] stop
				 */

				/**
				 * [tMap_7 end ] start
				 */

				currentComponent = "tMap_7";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row10");
				}

				ok_Hash.put("tMap_7", true);
				end_Hash.put("tMap_7", System.currentTimeMillis());

				/**
				 * [tMap_7 end ] stop
				 */

				/**
				 * [tMap_8 end ] start
				 */

				currentComponent = "tMap_8";

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row11 != null) {
					tHash_Lookup_row11.endGet();
				}
				globalMap.remove("tHash_Lookup_row11");

				if (tHash_Lookup_row13 != null) {
					tHash_Lookup_row13.endGet();
				}
				globalMap.remove("tHash_Lookup_row13");

				if (tHash_Lookup_row12 != null) {
					tHash_Lookup_row12.endGet();
				}
				globalMap.remove("tHash_Lookup_row12");

// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "copyOfout2");
				}

				ok_Hash.put("tMap_8", true);
				end_Hash.put("tMap_8", System.currentTimeMillis());

				/**
				 * [tMap_8 end ] stop
				 */

				/**
				 * [tFlowMeter_17 end ] start
				 */

				currentComponent = "tFlowMeter_17";

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "copyOfout1");
				}

				ok_Hash.put("tFlowMeter_17", true);
				end_Hash.put("tFlowMeter_17", System.currentTimeMillis());

				/**
				 * [tFlowMeter_17 end ] stop
				 */

				/**
				 * [tDBOutput_3 end ] start
				 */

				currentComponent = "tDBOutput_3";

				try {
					int countSum_tDBOutput_3 = 0;
					if (pstmt_tDBOutput_3 != null && batchSizeCounter_tDBOutput_3 > 0) {

						for (int countEach_tDBOutput_3 : pstmt_tDBOutput_3.executeBatch()) {
							if (countEach_tDBOutput_3 == -2 || countEach_tDBOutput_3 == -3) {
								break;
							}
							countSum_tDBOutput_3 += countEach_tDBOutput_3;
						}
						rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

					}

					insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

				} catch (java.sql.BatchUpdateException e) {
					globalMap.put("tDBOutput_3_ERROR_MESSAGE", e.getMessage());

					int countSum_tDBOutput_3 = 0;
					for (int countEach_tDBOutput_3 : e.getUpdateCounts()) {
						countSum_tDBOutput_3 += (countEach_tDBOutput_3 < 0 ? 0 : countEach_tDBOutput_3);
					}
					rowsToCommitCount_tDBOutput_3 += countSum_tDBOutput_3;

					insertedCount_tDBOutput_3 += countSum_tDBOutput_3;

					System.err.println(e.getMessage());

				}
				if (pstmt_tDBOutput_3 != null) {

					pstmt_tDBOutput_3.close();
					resourceMap.remove("pstmt_tDBOutput_3");

				}
				resourceMap.put("statementClosed_tDBOutput_3", true);
				if (rowsToCommitCount_tDBOutput_3 != 0) {

				}
				conn_tDBOutput_3.commit();
				if (rowsToCommitCount_tDBOutput_3 != 0) {

					rowsToCommitCount_tDBOutput_3 = 0;
				}
				commitCounter_tDBOutput_3 = 0;
				conn_tDBOutput_3.close();
				resourceMap.put("finish_tDBOutput_3", true);

				nb_line_deleted_tDBOutput_3 = nb_line_deleted_tDBOutput_3 + deletedCount_tDBOutput_3;
				nb_line_update_tDBOutput_3 = nb_line_update_tDBOutput_3 + updatedCount_tDBOutput_3;
				nb_line_inserted_tDBOutput_3 = nb_line_inserted_tDBOutput_3 + insertedCount_tDBOutput_3;
				nb_line_rejected_tDBOutput_3 = nb_line_rejected_tDBOutput_3 + rejectedCount_tDBOutput_3;

				globalMap.put("tDBOutput_3_NB_LINE", nb_line_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_UPDATED", nb_line_update_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_DELETED", nb_line_deleted_tDBOutput_3);
				globalMap.put("tDBOutput_3_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_3);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row14");
				}

				ok_Hash.put("tDBOutput_3", true);
				end_Hash.put("tDBOutput_3", System.currentTimeMillis());

				/**
				 * [tDBOutput_3 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			// free memory for "tMap_8"
			globalMap.remove("tHash_Lookup_row11");

			// free memory for "tMap_8"
			globalMap.remove("tHash_Lookup_row12");

			// free memory for "tMap_8"
			globalMap.remove("tHash_Lookup_row13");

			try {

				/**
				 * [tFileInputDelimited_4 finally ] start
				 */

				currentComponent = "tFileInputDelimited_4";

				/**
				 * [tFileInputDelimited_4 finally ] stop
				 */

				/**
				 * [tMap_7 finally ] start
				 */

				currentComponent = "tMap_7";

				/**
				 * [tMap_7 finally ] stop
				 */

				/**
				 * [tMap_8 finally ] start
				 */

				currentComponent = "tMap_8";

				/**
				 * [tMap_8 finally ] stop
				 */

				/**
				 * [tFlowMeter_17 finally ] start
				 */

				currentComponent = "tFlowMeter_17";

				/**
				 * [tFlowMeter_17 finally ] stop
				 */

				/**
				 * [tDBOutput_3 finally ] start
				 */

				currentComponent = "tDBOutput_3";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_3") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_3 = null;
						if ((pstmtToClose_tDBOutput_3 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_3")) != null) {
							pstmtToClose_tDBOutput_3.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_3") == null) {
						java.sql.Connection ctn_tDBOutput_3 = null;
						if ((ctn_tDBOutput_3 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_3")) != null) {
							try {
								ctn_tDBOutput_3.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_3) {
								String errorMessage_tDBOutput_3 = "failed to close the connection in tDBOutput_3 :"
										+ sqlEx_tDBOutput_3.getMessage();
								System.err.println(errorMessage_tDBOutput_3);
							}
						}
					}
				}

				/**
				 * [tDBOutput_3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_4_SUBPROCESS_STATE", 1);
	}

	public static class row11Struct implements routines.system.IPersistableComparableLookupRow<row11Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer userId;

		public Integer getUserId() {
			return this.userId;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.userId == null) ? 0 : this.userId.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row11Struct other = (row11Struct) obj;

			if (this.userId == null) {
				if (other.userId != null)
					return false;

			} else if (!this.userId.equals(other.userId))

				return false;

			return true;
		}

		public void copyDataTo(row11Struct other) {

			other.userId = this.userId;

		}

		public void copyKeysDataTo(row11Struct other) {

			other.userId = this.userId;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.userId = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.userId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

			}

			finally {
			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

			}

			finally {
			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

			} finally {
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

			} finally {
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("userId=" + String.valueOf(userId));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row11Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.userId, other.userId);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row11Struct row11 = new row11Struct();

				/**
				 * [tAdvancedHash_row11 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tAdvancedHash_row11", false);
				start_Hash.put("tAdvancedHash_row11", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row11";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row11");
				}

				int tos_count_tAdvancedHash_row11 = 0;

				// connection name:row11
				// source node:tFileInputDelimited_5 - inputs:(after_tFileInputDelimited_4)
				// outputs:(row11,row11) | target node:tAdvancedHash_row11 - inputs:(row11)
				// outputs:()
				// linked node: tMap_8 - inputs:(copyOfout2,row11,row12,row13)
				// outputs:(copyOfout1)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row11 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row11Struct> tHash_Lookup_row11 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row11Struct>getLookup(matchingModeEnum_row11);

				globalMap.put("tHash_Lookup_row11", tHash_Lookup_row11);

				/**
				 * [tAdvancedHash_row11 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_5 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tFileInputDelimited_5", false);
				start_Hash.put("tFileInputDelimited_5", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_5";

				int tos_count_tFileInputDelimited_5 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_5 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_5 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_5 = null;
				int limit_tFileInputDelimited_5 = -1;
				try {

					Object filename_tFileInputDelimited_5 = "C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/user.csv";
					if (filename_tFileInputDelimited_5 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_5 = 0, random_value_tFileInputDelimited_5 = -1;
						if (footer_value_tFileInputDelimited_5 > 0 || random_value_tFileInputDelimited_5 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_5 = new org.talend.fileprocess.FileInputDelimited(
								"C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/user.csv",
								"ISO-8859-15", ";", "\n", true, 1, 0, limit_tFileInputDelimited_5, -1, false);
					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_5_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					}

					while (fid_tFileInputDelimited_5 != null && fid_tFileInputDelimited_5.nextRecord()) {
						rowstate_tFileInputDelimited_5.reset();

						row11 = null;

						row11 = null;

						boolean whetherReject_tFileInputDelimited_5 = false;
						row11 = new row11Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_5 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_5 = 0;

							temp = fid_tFileInputDelimited_5.get(columnIndexWithD_tFileInputDelimited_5);
							if (temp.length() > 0) {

								try {

									row11.userId = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_5) {
									globalMap.put("tFileInputDelimited_5_ERROR_MESSAGE",
											ex_tFileInputDelimited_5.getMessage());
									rowstate_tFileInputDelimited_5.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"userId", "row11", temp, ex_tFileInputDelimited_5),
											ex_tFileInputDelimited_5));
								}

							} else {

								row11.userId = null;

							}

							if (rowstate_tFileInputDelimited_5.getException() != null) {
								throw rowstate_tFileInputDelimited_5.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_5_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_5 = true;

							System.err.println(e.getMessage());
							row11 = null;

						}

						/**
						 * [tFileInputDelimited_5 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_5 main ] start
						 */

						currentComponent = "tFileInputDelimited_5";

						tos_count_tFileInputDelimited_5++;

						/**
						 * [tFileInputDelimited_5 main ] stop
						 */

						/**
						 * [tFileInputDelimited_5 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_5";

						/**
						 * [tFileInputDelimited_5 process_data_begin ] stop
						 */
// Start of branch "row11"
						if (row11 != null) {

							/**
							 * [tAdvancedHash_row11 main ] start
							 */

							currentComponent = "tAdvancedHash_row11";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row11"

								);
							}

							row11Struct row11_HashRow = new row11Struct();

							row11_HashRow.userId = row11.userId;

							tHash_Lookup_row11.put(row11_HashRow);

							tos_count_tAdvancedHash_row11++;

							/**
							 * [tAdvancedHash_row11 main ] stop
							 */

							/**
							 * [tAdvancedHash_row11 process_data_begin ] start
							 */

							currentComponent = "tAdvancedHash_row11";

							/**
							 * [tAdvancedHash_row11 process_data_begin ] stop
							 */

							/**
							 * [tAdvancedHash_row11 process_data_end ] start
							 */

							currentComponent = "tAdvancedHash_row11";

							/**
							 * [tAdvancedHash_row11 process_data_end ] stop
							 */

						} // End of branch "row11"

						/**
						 * [tFileInputDelimited_5 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_5";

						/**
						 * [tFileInputDelimited_5 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_5 end ] start
						 */

						currentComponent = "tFileInputDelimited_5";

					}
				} finally {
					if (!((Object) ("C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/user.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_5 != null) {
							fid_tFileInputDelimited_5.close();
						}
					}
					if (fid_tFileInputDelimited_5 != null) {
						globalMap.put("tFileInputDelimited_5_NB_LINE", fid_tFileInputDelimited_5.getRowNumber());

					}
				}

				ok_Hash.put("tFileInputDelimited_5", true);
				end_Hash.put("tFileInputDelimited_5", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_5 end ] stop
				 */

				/**
				 * [tAdvancedHash_row11 end ] start
				 */

				currentComponent = "tAdvancedHash_row11";

				tHash_Lookup_row11.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row11");
				}

				ok_Hash.put("tAdvancedHash_row11", true);
				end_Hash.put("tAdvancedHash_row11", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row11 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_5 finally ] start
				 */

				currentComponent = "tFileInputDelimited_5";

				/**
				 * [tFileInputDelimited_5 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row11 finally ] start
				 */

				currentComponent = "tAdvancedHash_row11";

				/**
				 * [tAdvancedHash_row11 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_5_SUBPROCESS_STATE", 1);
	}

	public static class row12Struct implements routines.system.IPersistableComparableLookupRow<row12Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer movieId;

		public Integer getMovieId() {
			return this.movieId;
		}

		public String cleaned_title;

		public String getCleaned_title() {
			return this.cleaned_title;
		}

		public Integer year;

		public Integer getYear() {
			return this.year;
		}

		public String genre1;

		public String getGenre1() {
			return this.genre1;
		}

		public String genre2;

		public String getGenre2() {
			return this.genre2;
		}

		public String genre3;

		public String getGenre3() {
			return this.genre3;
		}

		public String genre4;

		public String getGenre4() {
			return this.genre4;
		}

		public String genre5;

		public String getGenre5() {
			return this.genre5;
		}

		public String genre6;

		public String getGenre6() {
			return this.genre6;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.movieId == null) ? 0 : this.movieId.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row12Struct other = (row12Struct) obj;

			if (this.movieId == null) {
				if (other.movieId != null)
					return false;

			} else if (!this.movieId.equals(other.movieId))

				return false;

			return true;
		}

		public void copyDataTo(row12Struct other) {

			other.movieId = this.movieId;
			other.cleaned_title = this.cleaned_title;
			other.year = this.year;
			other.genre1 = this.genre1;
			other.genre2 = this.genre2;
			other.genre3 = this.genre3;
			other.genre4 = this.genre4;
			other.genre5 = this.genre5;
			other.genre6 = this.genre6;

		}

		public void copyKeysDataTo(row12Struct other) {

			other.movieId = this.movieId;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.movieId = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.movieId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.movieId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.cleaned_title = readString(dis, ois);

				this.year = readInteger(dis, ois);

				this.genre1 = readString(dis, ois);

				this.genre2 = readString(dis, ois);

				this.genre3 = readString(dis, ois);

				this.genre4 = readString(dis, ois);

				this.genre5 = readString(dis, ois);

				this.genre6 = readString(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.cleaned_title = readString(dis, objectIn);

				this.year = readInteger(dis, objectIn);

				this.genre1 = readString(dis, objectIn);

				this.genre2 = readString(dis, objectIn);

				this.genre3 = readString(dis, objectIn);

				this.genre4 = readString(dis, objectIn);

				this.genre5 = readString(dis, objectIn);

				this.genre6 = readString(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeString(this.cleaned_title, dos, oos);

				writeInteger(this.year, dos, oos);

				writeString(this.genre1, dos, oos);

				writeString(this.genre2, dos, oos);

				writeString(this.genre3, dos, oos);

				writeString(this.genre4, dos, oos);

				writeString(this.genre5, dos, oos);

				writeString(this.genre6, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeString(this.cleaned_title, dos, objectOut);

				writeInteger(this.year, dos, objectOut);

				writeString(this.genre1, dos, objectOut);

				writeString(this.genre2, dos, objectOut);

				writeString(this.genre3, dos, objectOut);

				writeString(this.genre4, dos, objectOut);

				writeString(this.genre5, dos, objectOut);

				writeString(this.genre6, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("movieId=" + String.valueOf(movieId));
			sb.append(",cleaned_title=" + cleaned_title);
			sb.append(",year=" + String.valueOf(year));
			sb.append(",genre1=" + genre1);
			sb.append(",genre2=" + genre2);
			sb.append(",genre3=" + genre3);
			sb.append(",genre4=" + genre4);
			sb.append(",genre5=" + genre5);
			sb.append(",genre6=" + genre6);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row12Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.movieId, other.movieId);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_6Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_6_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row12Struct row12 = new row12Struct();

				/**
				 * [tAdvancedHash_row12 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tAdvancedHash_row12", false);
				start_Hash.put("tAdvancedHash_row12", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row12";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row12");
				}

				int tos_count_tAdvancedHash_row12 = 0;

				// connection name:row12
				// source node:tFileInputDelimited_6 - inputs:(after_tFileInputDelimited_4)
				// outputs:(row12,row12) | target node:tAdvancedHash_row12 - inputs:(row12)
				// outputs:()
				// linked node: tMap_8 - inputs:(copyOfout2,row11,row12,row13)
				// outputs:(copyOfout1)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row12 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row12Struct> tHash_Lookup_row12 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row12Struct>getLookup(matchingModeEnum_row12);

				globalMap.put("tHash_Lookup_row12", tHash_Lookup_row12);

				/**
				 * [tAdvancedHash_row12 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_6 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tFileInputDelimited_6", false);
				start_Hash.put("tFileInputDelimited_6", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_6";

				int tos_count_tFileInputDelimited_6 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_6 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_6 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_6 = null;
				int limit_tFileInputDelimited_6 = -1;
				try {

					Object filename_tFileInputDelimited_6 = "C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/valid_movies.csv";
					if (filename_tFileInputDelimited_6 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_6 = 0, random_value_tFileInputDelimited_6 = -1;
						if (footer_value_tFileInputDelimited_6 > 0 || random_value_tFileInputDelimited_6 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_6 = new org.talend.fileprocess.FileInputDelimited(
								"C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/valid_movies.csv",
								"UTF-8", ";", "\n", false, 1, 0, limit_tFileInputDelimited_6, -1, false);
					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_6_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					}

					while (fid_tFileInputDelimited_6 != null && fid_tFileInputDelimited_6.nextRecord()) {
						rowstate_tFileInputDelimited_6.reset();

						row12 = null;

						row12 = null;

						boolean whetherReject_tFileInputDelimited_6 = false;
						row12 = new row12Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_6 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_6 = 0;

							temp = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);
							if (temp.length() > 0) {

								try {

									row12.movieId = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_6) {
									globalMap.put("tFileInputDelimited_6_ERROR_MESSAGE",
											ex_tFileInputDelimited_6.getMessage());
									rowstate_tFileInputDelimited_6.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"movieId", "row12", temp, ex_tFileInputDelimited_6),
											ex_tFileInputDelimited_6));
								}

							} else {

								row12.movieId = null;

							}

							columnIndexWithD_tFileInputDelimited_6 = 1;

							row12.cleaned_title = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);

							columnIndexWithD_tFileInputDelimited_6 = 2;

							temp = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);
							if (temp.length() > 0) {

								try {

									row12.year = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_6) {
									globalMap.put("tFileInputDelimited_6_ERROR_MESSAGE",
											ex_tFileInputDelimited_6.getMessage());
									rowstate_tFileInputDelimited_6.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"year", "row12", temp, ex_tFileInputDelimited_6),
											ex_tFileInputDelimited_6));
								}

							} else {

								row12.year = null;

							}

							columnIndexWithD_tFileInputDelimited_6 = 3;

							row12.genre1 = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);

							columnIndexWithD_tFileInputDelimited_6 = 4;

							row12.genre2 = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);

							columnIndexWithD_tFileInputDelimited_6 = 5;

							row12.genre3 = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);

							columnIndexWithD_tFileInputDelimited_6 = 6;

							row12.genre4 = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);

							columnIndexWithD_tFileInputDelimited_6 = 7;

							row12.genre5 = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);

							columnIndexWithD_tFileInputDelimited_6 = 8;

							row12.genre6 = fid_tFileInputDelimited_6.get(columnIndexWithD_tFileInputDelimited_6);

							if (rowstate_tFileInputDelimited_6.getException() != null) {
								throw rowstate_tFileInputDelimited_6.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_6_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_6 = true;

							System.err.println(e.getMessage());
							row12 = null;

						}

						/**
						 * [tFileInputDelimited_6 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_6 main ] start
						 */

						currentComponent = "tFileInputDelimited_6";

						tos_count_tFileInputDelimited_6++;

						/**
						 * [tFileInputDelimited_6 main ] stop
						 */

						/**
						 * [tFileInputDelimited_6 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_6";

						/**
						 * [tFileInputDelimited_6 process_data_begin ] stop
						 */
// Start of branch "row12"
						if (row12 != null) {

							/**
							 * [tAdvancedHash_row12 main ] start
							 */

							currentComponent = "tAdvancedHash_row12";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row12"

								);
							}

							row12Struct row12_HashRow = new row12Struct();

							row12_HashRow.movieId = row12.movieId;

							row12_HashRow.cleaned_title = row12.cleaned_title;

							row12_HashRow.year = row12.year;

							row12_HashRow.genre1 = row12.genre1;

							row12_HashRow.genre2 = row12.genre2;

							row12_HashRow.genre3 = row12.genre3;

							row12_HashRow.genre4 = row12.genre4;

							row12_HashRow.genre5 = row12.genre5;

							row12_HashRow.genre6 = row12.genre6;

							tHash_Lookup_row12.put(row12_HashRow);

							tos_count_tAdvancedHash_row12++;

							/**
							 * [tAdvancedHash_row12 main ] stop
							 */

							/**
							 * [tAdvancedHash_row12 process_data_begin ] start
							 */

							currentComponent = "tAdvancedHash_row12";

							/**
							 * [tAdvancedHash_row12 process_data_begin ] stop
							 */

							/**
							 * [tAdvancedHash_row12 process_data_end ] start
							 */

							currentComponent = "tAdvancedHash_row12";

							/**
							 * [tAdvancedHash_row12 process_data_end ] stop
							 */

						} // End of branch "row12"

						/**
						 * [tFileInputDelimited_6 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_6";

						/**
						 * [tFileInputDelimited_6 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_6 end ] start
						 */

						currentComponent = "tFileInputDelimited_6";

					}
				} finally {
					if (!((Object) ("C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/valid_movies.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_6 != null) {
							fid_tFileInputDelimited_6.close();
						}
					}
					if (fid_tFileInputDelimited_6 != null) {
						globalMap.put("tFileInputDelimited_6_NB_LINE", fid_tFileInputDelimited_6.getRowNumber());

					}
				}

				ok_Hash.put("tFileInputDelimited_6", true);
				end_Hash.put("tFileInputDelimited_6", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_6 end ] stop
				 */

				/**
				 * [tAdvancedHash_row12 end ] start
				 */

				currentComponent = "tAdvancedHash_row12";

				tHash_Lookup_row12.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row12");
				}

				ok_Hash.put("tAdvancedHash_row12", true);
				end_Hash.put("tAdvancedHash_row12", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row12 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_6 finally ] start
				 */

				currentComponent = "tFileInputDelimited_6";

				/**
				 * [tFileInputDelimited_6 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row12 finally ] start
				 */

				currentComponent = "tAdvancedHash_row12";

				/**
				 * [tAdvancedHash_row12 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_6_SUBPROCESS_STATE", 1);
	}

	public static class row13Struct implements routines.system.IPersistableComparableLookupRow<row13Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_tParallelize = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_tParallelize = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public Integer DateKey;

		public Integer getDateKey() {
			return this.DateKey;
		}

		public Integer mouth;

		public Integer getMouth() {
			return this.mouth;
		}

		public Integer year;

		public Integer getYear() {
			return this.year;
		}

		public Integer day;

		public Integer getDay() {
			return this.day;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.DateKey == null) ? 0 : this.DateKey.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row13Struct other = (row13Struct) obj;

			if (this.DateKey == null) {
				if (other.DateKey != null)
					return false;

			} else if (!this.DateKey.equals(other.DateKey))

				return false;

			return true;
		}

		public void copyDataTo(row13Struct other) {

			other.DateKey = this.DateKey;
			other.mouth = this.mouth;
			other.year = this.year;
			other.day = this.day;

		}

		public void copyKeysDataTo(row13Struct other) {

			other.DateKey = this.DateKey;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.DateKey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_tParallelize) {

				try {

					int length = 0;

					this.DateKey = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// Integer

				writeInteger(this.DateKey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// Integer

				writeInteger(this.DateKey, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.mouth = readInteger(dis, ois);

				this.year = readInteger(dis, ois);

				this.day = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.mouth = readInteger(dis, objectIn);

				this.year = readInteger(dis, objectIn);

				this.day = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeInteger(this.mouth, dos, oos);

				writeInteger(this.year, dos, oos);

				writeInteger(this.day, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeInteger(this.mouth, dos, objectOut);

				writeInteger(this.year, dos, objectOut);

				writeInteger(this.day, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("DateKey=" + String.valueOf(DateKey));
			sb.append(",mouth=" + String.valueOf(mouth));
			sb.append(",year=" + String.valueOf(year));
			sb.append(",day=" + String.valueOf(day));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row13Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.DateKey, other.DateKey);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_7Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_7_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row13Struct row13 = new row13Struct();

				/**
				 * [tAdvancedHash_row13 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tAdvancedHash_row13", false);
				start_Hash.put("tAdvancedHash_row13", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row13";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row13");
				}

				int tos_count_tAdvancedHash_row13 = 0;

				// connection name:row13
				// source node:tFileInputDelimited_7 - inputs:(after_tFileInputDelimited_4)
				// outputs:(row13,row13) | target node:tAdvancedHash_row13 - inputs:(row13)
				// outputs:()
				// linked node: tMap_8 - inputs:(copyOfout2,row11,row12,row13)
				// outputs:(copyOfout1)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row13 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row13Struct> tHash_Lookup_row13 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row13Struct>getLookup(matchingModeEnum_row13);

				globalMap.put("tHash_Lookup_row13", tHash_Lookup_row13);

				/**
				 * [tAdvancedHash_row13 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_7 begin ] start
				 */

				globalMap.put("ENABLE_TRACES_CONNECTION_tFileInputDelimited_4", Boolean.FALSE);

				ok_Hash.put("tFileInputDelimited_7", false);
				start_Hash.put("tFileInputDelimited_7", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_7";

				int tos_count_tFileInputDelimited_7 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_7 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_7 = 0;
				org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_7 = null;
				int limit_tFileInputDelimited_7 = -1;
				try {

					Object filename_tFileInputDelimited_7 = "C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/Date.csv";
					if (filename_tFileInputDelimited_7 instanceof java.io.InputStream) {

						int footer_value_tFileInputDelimited_7 = 0, random_value_tFileInputDelimited_7 = -1;
						if (footer_value_tFileInputDelimited_7 > 0 || random_value_tFileInputDelimited_7 > 0) {
							throw new java.lang.Exception(
									"When the input source is a stream,footer and random shouldn't be bigger than 0.");
						}

					}
					try {
						fid_tFileInputDelimited_7 = new org.talend.fileprocess.FileInputDelimited(
								"C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/Date.csv",
								"ISO-8859-15", ";", "\n", true, 1, 0, limit_tFileInputDelimited_7, -1, false);
					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_7_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					}

					while (fid_tFileInputDelimited_7 != null && fid_tFileInputDelimited_7.nextRecord()) {
						rowstate_tFileInputDelimited_7.reset();

						row13 = null;

						row13 = null;

						boolean whetherReject_tFileInputDelimited_7 = false;
						row13 = new row13Struct();
						try {

							int columnIndexWithD_tFileInputDelimited_7 = 0;

							String temp = "";

							columnIndexWithD_tFileInputDelimited_7 = 0;

							temp = fid_tFileInputDelimited_7.get(columnIndexWithD_tFileInputDelimited_7);
							if (temp.length() > 0) {

								try {

									row13.DateKey = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_7) {
									globalMap.put("tFileInputDelimited_7_ERROR_MESSAGE",
											ex_tFileInputDelimited_7.getMessage());
									rowstate_tFileInputDelimited_7.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"DateKey", "row13", temp, ex_tFileInputDelimited_7),
											ex_tFileInputDelimited_7));
								}

							} else {

								row13.DateKey = null;

							}

							columnIndexWithD_tFileInputDelimited_7 = 1;

							temp = fid_tFileInputDelimited_7.get(columnIndexWithD_tFileInputDelimited_7);
							if (temp.length() > 0) {

								try {

									row13.mouth = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_7) {
									globalMap.put("tFileInputDelimited_7_ERROR_MESSAGE",
											ex_tFileInputDelimited_7.getMessage());
									rowstate_tFileInputDelimited_7.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"mouth", "row13", temp, ex_tFileInputDelimited_7),
											ex_tFileInputDelimited_7));
								}

							} else {

								row13.mouth = null;

							}

							columnIndexWithD_tFileInputDelimited_7 = 2;

							temp = fid_tFileInputDelimited_7.get(columnIndexWithD_tFileInputDelimited_7);
							if (temp.length() > 0) {

								try {

									row13.year = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_7) {
									globalMap.put("tFileInputDelimited_7_ERROR_MESSAGE",
											ex_tFileInputDelimited_7.getMessage());
									rowstate_tFileInputDelimited_7.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"year", "row13", temp, ex_tFileInputDelimited_7),
											ex_tFileInputDelimited_7));
								}

							} else {

								row13.year = null;

							}

							columnIndexWithD_tFileInputDelimited_7 = 3;

							temp = fid_tFileInputDelimited_7.get(columnIndexWithD_tFileInputDelimited_7);
							if (temp.length() > 0) {

								try {

									row13.day = ParserUtils.parseTo_Integer(temp);

								} catch (java.lang.Exception ex_tFileInputDelimited_7) {
									globalMap.put("tFileInputDelimited_7_ERROR_MESSAGE",
											ex_tFileInputDelimited_7.getMessage());
									rowstate_tFileInputDelimited_7.setException(new RuntimeException(String.format(
											"Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
											"day", "row13", temp, ex_tFileInputDelimited_7), ex_tFileInputDelimited_7));
								}

							} else {

								row13.day = null;

							}

							if (rowstate_tFileInputDelimited_7.getException() != null) {
								throw rowstate_tFileInputDelimited_7.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_7_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_7 = true;

							System.err.println(e.getMessage());
							row13 = null;

						}

						/**
						 * [tFileInputDelimited_7 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_7 main ] start
						 */

						currentComponent = "tFileInputDelimited_7";

						tos_count_tFileInputDelimited_7++;

						/**
						 * [tFileInputDelimited_7 main ] stop
						 */

						/**
						 * [tFileInputDelimited_7 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_7";

						/**
						 * [tFileInputDelimited_7 process_data_begin ] stop
						 */
// Start of branch "row13"
						if (row13 != null) {

							/**
							 * [tAdvancedHash_row13 main ] start
							 */

							currentComponent = "tAdvancedHash_row13";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row13"

								);
							}

							row13Struct row13_HashRow = new row13Struct();

							row13_HashRow.DateKey = row13.DateKey;

							row13_HashRow.mouth = row13.mouth;

							row13_HashRow.year = row13.year;

							row13_HashRow.day = row13.day;

							tHash_Lookup_row13.put(row13_HashRow);

							tos_count_tAdvancedHash_row13++;

							/**
							 * [tAdvancedHash_row13 main ] stop
							 */

							/**
							 * [tAdvancedHash_row13 process_data_begin ] start
							 */

							currentComponent = "tAdvancedHash_row13";

							/**
							 * [tAdvancedHash_row13 process_data_begin ] stop
							 */

							/**
							 * [tAdvancedHash_row13 process_data_end ] start
							 */

							currentComponent = "tAdvancedHash_row13";

							/**
							 * [tAdvancedHash_row13 process_data_end ] stop
							 */

						} // End of branch "row13"

						/**
						 * [tFileInputDelimited_7 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_7";

						/**
						 * [tFileInputDelimited_7 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_7 end ] start
						 */

						currentComponent = "tFileInputDelimited_7";

					}
				} finally {
					if (!((Object) ("C:/Users/Youcode/Desktop/projet_2025/workspace/projet_data/pipeline_MovieLens/output/Date.csv") instanceof java.io.InputStream)) {
						if (fid_tFileInputDelimited_7 != null) {
							fid_tFileInputDelimited_7.close();
						}
					}
					if (fid_tFileInputDelimited_7 != null) {
						globalMap.put("tFileInputDelimited_7_NB_LINE", fid_tFileInputDelimited_7.getRowNumber());

					}
				}

				ok_Hash.put("tFileInputDelimited_7", true);
				end_Hash.put("tFileInputDelimited_7", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_7 end ] stop
				 */

				/**
				 * [tAdvancedHash_row13 end ] start
				 */

				currentComponent = "tAdvancedHash_row13";

				tHash_Lookup_row13.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row13");
				}

				ok_Hash.put("tAdvancedHash_row13", true);
				end_Hash.put("tAdvancedHash_row13", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row13 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tFileInputDelimited_7 finally ] start
				 */

				currentComponent = "tFileInputDelimited_7";

				/**
				 * [tFileInputDelimited_7 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row13 finally ] start
				 */

				currentComponent = "tAdvancedHash_row13";

				/**
				 * [tAdvancedHash_row13 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_7_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final tParallelize tParallelizeClass = new tParallelize();

		int exitCode = tParallelizeClass.runJobInTOS(args);

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null && jobProperties.get("context") != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = tParallelize.class.getClassLoader()
					.getResourceAsStream("pipline_complite/tparallelize_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = tParallelize.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		try {
			runTrace.openSocket(!isChildJob);
			runTrace.startThreadTrace(clientHost, portTraces);
			if (runTrace.isPause()) {
				while (runTrace.isPause()) {
					Thread.sleep(100);
				}
			}
		} catch (java.io.IOException ioException) {
			ioException.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputDelimited_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_1) {
			globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_1.printStackTrace();

		}
		try {
			errorCode = null;
			tFileInputDelimited_2Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_2) {
			globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_2.printStackTrace();

		}
		try {
			errorCode = null;
			tFileInputDelimited_3Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_3) {
			globalMap.put("tFileInputDelimited_3_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_3.printStackTrace();

		}
		try {
			errorCode = null;
			tFileInputDelimited_4Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_4) {
			globalMap.put("tFileInputDelimited_4_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_4.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out
					.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : tParallelize");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		runTrace.stopThreadTrace();
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 406387 characters generated by Talend Open Studio for Data Integration on the
 * 10 janvier 2025, 10:18:11 CET
 ************************************************************************************************/