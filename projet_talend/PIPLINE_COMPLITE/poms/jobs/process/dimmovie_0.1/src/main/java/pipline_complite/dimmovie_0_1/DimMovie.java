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

package pipline_complite.dimmovie_0_1;

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
 * Job: DimMovie Purpose: <br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class DimMovie implements TalendJob {

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

			if (ENV != null) {

				this.setProperty("ENV", ENV.toString());

			}

			if (input_csv != null) {

				this.setProperty("input_csv", input_csv.toString());

			}

			if (output_csv != null) {

				this.setProperty("output_csv", output_csv.toString());

			}

			if (input_file != null) {

				this.setProperty("input_file", input_file.toString());

			}

			if (output_file != null) {

				this.setProperty("output_file", output_file.toString());

			}

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

		public String ENV;

		public String getENV() {
			return this.ENV;
		}

		public String input_csv;

		public String getInput_csv() {
			return this.input_csv;
		}

		public String output_csv;

		public String getOutput_csv() {
			return this.output_csv;
		}

		public String input_file;

		public String getInput_file() {
			return this.input_file;
		}

		public String output_file;

		public String getOutput_file() {
			return this.output_file;
		}
	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "DimMovie";
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
					DimMovie.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(DimMovie.this, new Object[] { e, currentComponent, globalMap });
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

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tSchemaComplianceCheck_1_error(Exception exception, String errorComponent,
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

	public void tFlowMeter_9_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row6Struct implements routines.system.IPersistableRow<row6Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_DimMovie = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[0];

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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

	public static class split_genreStruct implements routines.system.IPersistableRow<split_genreStruct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_DimMovie = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[0];

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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_DimMovie = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[0];

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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

	public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_DimMovie = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[0];

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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_DimMovie = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[0];

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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

	public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_DimMovie = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[0];

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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_DimMovie = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[0];

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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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
		final static byte[] commonByteArrayLock_PIPLINE_COMPLITE_DimMovie = new byte[0];
		static byte[] commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[0];

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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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
				if (length > commonByteArray_PIPLINE_COMPLITE_DimMovie.length) {
					if (length < 1024 && commonByteArray_PIPLINE_COMPLITE_DimMovie.length == 0) {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[1024];
					} else {
						commonByteArray_PIPLINE_COMPLITE_DimMovie = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length);
				strReturn = new String(commonByteArray_PIPLINE_COMPLITE_DimMovie, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

			synchronized (commonByteArrayLock_PIPLINE_COMPLITE_DimMovie) {

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

				row1Struct row1 = new row1Struct();
				row1Struct row2 = row1;
				row3Struct row3 = new row3Struct();
				year_validStruct year_valid = new year_validStruct();
				split_genreStruct split_genre = new split_genreStruct();
				split_genreStruct row6 = split_genre;
				row4Struct row4 = new row4Struct();
				row4Struct row5 = row4;

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row6");
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
						"enc:routine.encryption.key.v1:/eoqETy8H56eEAl1m2PkFUJfZUGbClGcn5aD2MNPvsOL3L0=");

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
				 * [tFlowMeter_9 begin ] start
				 */

				ok_Hash.put("tFlowMeter_9", false);
				start_Hash.put("tFlowMeter_9", System.currentTimeMillis());

				currentComponent = "tFlowMeter_9";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "split_genre");
				}

				int tos_count_tFlowMeter_9 = 0;

				int count_tFlowMeter_9 = 0;

				/**
				 * [tFlowMeter_9 begin ] stop
				 */

				/**
				 * [tMap_2 begin ] start
				 */

				ok_Hash.put("tMap_2", false);
				start_Hash.put("tMap_2", System.currentTimeMillis());

				currentComponent = "tMap_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "year_valid");
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
				split_genreStruct split_genre_tmp = new split_genreStruct();
// ###############################

				/**
				 * [tMap_2 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

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
				year_validStruct year_valid_tmp = new year_validStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tLogRow_2 begin ] start
				 */

				ok_Hash.put("tLogRow_2", false);
				start_Hash.put("tLogRow_2", System.currentTimeMillis());

				currentComponent = "tLogRow_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row5");
				}

				int tos_count_tLogRow_2 = 0;

				///////////////////////

				final String OUTPUT_FIELD_SEPARATOR_tLogRow_2 = "|";
				java.io.PrintStream consoleOut_tLogRow_2 = null;

				StringBuilder strBuffer_tLogRow_2 = null;
				int nb_line_tLogRow_2 = 0;
///////////////////////    			

				/**
				 * [tLogRow_2 begin ] stop
				 */

				/**
				 * [tLogRow_3 begin ] start
				 */

				ok_Hash.put("tLogRow_3", false);
				start_Hash.put("tLogRow_3", System.currentTimeMillis());

				currentComponent = "tLogRow_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row4");
				}

				int tos_count_tLogRow_3 = 0;

				///////////////////////

				class Util_tLogRow_3 {

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
				Util_tLogRow_3 util_tLogRow_3 = new Util_tLogRow_3();
				util_tLogRow_3.setTableName("null_data");
				util_tLogRow_3.addRow(new String[] { "movieId", "title", "genres", "errorCode", "errorMessage", });
				StringBuilder strBuffer_tLogRow_3 = null;
				int nb_line_tLogRow_3 = 0;
///////////////////////    			

				/**
				 * [tLogRow_3 begin ] stop
				 */

				/**
				 * [tSchemaComplianceCheck_1 begin ] start
				 */

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
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				int footer_tFileInputDelimited_1 = 0;
				int totalLinetFileInputDelimited_1 = 0;
				int limittFileInputDelimited_1 = -1;
				int lastLinetFileInputDelimited_1 = -1;

				char fieldSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ",").length() > 0) {
					fieldSeparator_tFileInputDelimited_1 = ((String) ",").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_1 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_1 = /** Start field tFileInputDelimited_1:FILENAME */
						context.input_file + "movies.csv"/** End field tFileInputDelimited_1:FILENAME */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_1 = null;

				try {

					String[] rowtFileInputDelimited_1 = null;
					int currentLinetFileInputDelimited_1 = 0;
					int outputLinetFileInputDelimited_1 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_1 = 0;
							if (footer_value_tFileInputDelimited_1 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_1,
									fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
						} else {
							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_1),
									fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
						}

						csvReadertFileInputDelimited_1.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
							csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

						csvReadertFileInputDelimited_1.setQuoteChar('"');

						csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						if (footer_tFileInputDelimited_1 > 0) {
							for (totalLinetFileInputDelimited_1 = 0; totalLinetFileInputDelimited_1 < 1; totalLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
							csvReadertFileInputDelimited_1.setSkipEmptyRecords(false);
							while (csvReadertFileInputDelimited_1.readNext()) {

								totalLinetFileInputDelimited_1++;

							}
							int lastLineTemptFileInputDelimited_1 = totalLinetFileInputDelimited_1
									- footer_tFileInputDelimited_1 < 0 ? 0
											: totalLinetFileInputDelimited_1 - footer_tFileInputDelimited_1;
							if (lastLinetFileInputDelimited_1 > 0) {
								lastLinetFileInputDelimited_1 = lastLinetFileInputDelimited_1 < lastLineTemptFileInputDelimited_1
										? lastLinetFileInputDelimited_1
										: lastLineTemptFileInputDelimited_1;
							} else {
								lastLinetFileInputDelimited_1 = lastLineTemptFileInputDelimited_1;
							}

							csvReadertFileInputDelimited_1.close();
							if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_1,
										fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
							} else {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_1),
										fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
							}
							csvReadertFileInputDelimited_1.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
								csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

							csvReadertFileInputDelimited_1.setQuoteChar('"');

							csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						}

						if (limittFileInputDelimited_1 != 0) {
							for (currentLinetFileInputDelimited_1 = 0; currentLinetFileInputDelimited_1 < 1; currentLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
						}
						csvReadertFileInputDelimited_1.setSkipEmptyRecords(false);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_1 != 0 && csvReadertFileInputDelimited_1 != null
							&& csvReadertFileInputDelimited_1.readNext()) {
						rowstate_tFileInputDelimited_1.reset();

						rowtFileInputDelimited_1 = csvReadertFileInputDelimited_1.getValues();

						currentLinetFileInputDelimited_1++;

						if (lastLinetFileInputDelimited_1 > -1
								&& currentLinetFileInputDelimited_1 > lastLinetFileInputDelimited_1) {
							break;
						}
						outputLinetFileInputDelimited_1++;
						if (limittFileInputDelimited_1 > 0
								&& outputLinetFileInputDelimited_1 > limittFileInputDelimited_1) {
							break;
						}

						row1 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row1 = new row1Struct();
						try {

							char fieldSeparator_tFileInputDelimited_1_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ",").length() > 0) {
								fieldSeparator_tFileInputDelimited_1_ListType = ((String) ",").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])) {// empty
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

								int columnIndexWithD_tFileInputDelimited_1 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_1 = 0;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.movieId = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"movieId", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.movieId = null;

									}

								} else {

									row1.movieId = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 1;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.title = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.title = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 2;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.genres = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.genres = null;

								}

							}

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							System.err.println(e.getMessage());
							row1 = null;

							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

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
// Start of branch "row1"
						if (row1 != null) {
							row4 = null;

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

							row3 = null;
							row4 = null;
							rsvUtil_tSchemaComplianceCheck_1.setRowValue_0(row2);
							if (rsvUtil_tSchemaComplianceCheck_1.ifPassedThrough) {
								row3 = new row3Struct();
								row3.movieId = row2.movieId;
								row3.title = row2.title;
								row3.genres = row2.genres;
							}
							if (!rsvUtil_tSchemaComplianceCheck_1.ifPassedThrough) {
								row4 = new row4Struct();
								row4.movieId = row2.movieId;
								row4.title = row2.title;
								row4.genres = row2.genres;
								row4.errorCode = String
										.valueOf(rsvUtil_tSchemaComplianceCheck_1.resultErrorCodeThrough);
								row4.errorMessage = rsvUtil_tSchemaComplianceCheck_1.resultErrorMessageThrough;
							}
							rsvUtil_tSchemaComplianceCheck_1.reset();

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

									year_valid = null;

// # Output table : 'year_valid'
									year_valid_tmp.movieId = row3.movieId;
									year_valid_tmp.cleaned_title = (row3.title != null
											&& row3.title.matches(".*\\(\\d{4}\\).*"))
													? row3.title.replaceAll("\\(\\d{4}\\)", "")
													: row3.title;
									year_valid_tmp.year = (row3.title != null && row3.title.matches(".*\\(\\d{4}\\).*"))
											? row3.title.replaceAll(".*\\((\\d{4})\\).*", "$1")
											: null;
									;
									year_valid_tmp.genres = row3.genres;
									year_valid = year_valid_tmp;
// ###############################

								} // end of Var scope

								rejectedInnerJoin_tMap_1 = false;

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
// Start of branch "year_valid"
								if (year_valid != null) {

									/**
									 * [tMap_2 main ] start
									 */

									currentComponent = "tMap_2";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "year_valid"

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

									rejectedInnerJoin_tMap_2 = false;

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
// Start of branch "split_genre"
									if (split_genre != null) {

										/**
										 * [tFlowMeter_9 main ] start
										 */

										currentComponent = "tFlowMeter_9";

										if (execStat) {
											runStat.updateStatOnConnection(iterateId, 1, 1

													, "split_genre"

											);
										}

										count_tFlowMeter_9++;

										row6 = split_genre;

										tos_count_tFlowMeter_9++;

										/**
										 * [tFlowMeter_9 main ] stop
										 */

										/**
										 * [tFlowMeter_9 process_data_begin ] start
										 */

										currentComponent = "tFlowMeter_9";

										/**
										 * [tFlowMeter_9 process_data_begin ] stop
										 */

										/**
										 * [tDBOutput_1 main ] start
										 */

										currentComponent = "tDBOutput_1";

										if (execStat) {
											runStat.updateStatOnConnection(iterateId, 1, 1

													, "row6"

											);
										}

										whetherReject_tDBOutput_1 = false;
										pstmt_tDBOutput_1.setInt(1, row6.movieId);

										if (row6.cleaned_title == null) {
											pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(2, row6.cleaned_title);
										}

										if (row6.year == null) {
											pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(3, row6.year);
										}

										if (row6.genre1 == null) {
											pstmt_tDBOutput_1.setNull(4, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(4, row6.genre1);
										}

										if (row6.genre2 == null) {
											pstmt_tDBOutput_1.setNull(5, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(5, row6.genre2);
										}

										if (row6.genre3 == null) {
											pstmt_tDBOutput_1.setNull(6, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(6, row6.genre3);
										}

										if (row6.genre4 == null) {
											pstmt_tDBOutput_1.setNull(7, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(7, row6.genre4);
										}

										if (row6.genre5 == null) {
											pstmt_tDBOutput_1.setNull(8, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(8, row6.genre5);
										}

										if (row6.genre6 == null) {
											pstmt_tDBOutput_1.setNull(9, java.sql.Types.VARCHAR);
										} else {
											pstmt_tDBOutput_1.setString(9, row6.genre6);
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
										 * [tFlowMeter_9 process_data_end ] start
										 */

										currentComponent = "tFlowMeter_9";

										/**
										 * [tFlowMeter_9 process_data_end ] stop
										 */

									} // End of branch "split_genre"

									/**
									 * [tMap_2 process_data_end ] start
									 */

									currentComponent = "tMap_2";

									/**
									 * [tMap_2 process_data_end ] stop
									 */

								} // End of branch "year_valid"

								/**
								 * [tMap_1 process_data_end ] start
								 */

								currentComponent = "tMap_1";

								/**
								 * [tMap_1 process_data_end ] stop
								 */

							} // End of branch "row3"

// Start of branch "row4"
							if (row4 != null) {

								/**
								 * [tLogRow_3 main ] start
								 */

								currentComponent = "tLogRow_3";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row4"

									);
								}

///////////////////////		

								String[] row_tLogRow_3 = new String[5];

								row_tLogRow_3[0] = String.valueOf(row4.movieId);

								if (row4.title != null) { //
									row_tLogRow_3[1] = String.valueOf(row4.title);

								} //

								if (row4.genres != null) { //
									row_tLogRow_3[2] = String.valueOf(row4.genres);

								} //

								if (row4.errorCode != null) { //
									row_tLogRow_3[3] = String.valueOf(row4.errorCode);

								} //

								if (row4.errorMessage != null) { //
									row_tLogRow_3[4] = String.valueOf(row4.errorMessage);

								} //

								util_tLogRow_3.addRow(row_tLogRow_3);
								nb_line_tLogRow_3++;
//////

//////                    

///////////////////////    			

								row5 = row4;

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
								 * [tLogRow_2 main ] start
								 */

								currentComponent = "tLogRow_2";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row5"

									);
								}

///////////////////////		

								strBuffer_tLogRow_2 = new StringBuilder();

								strBuffer_tLogRow_2.append(String.valueOf(row5.movieId));

								strBuffer_tLogRow_2.append("|");

								if (row5.title != null) { //

									strBuffer_tLogRow_2.append(String.valueOf(row5.title));

								} //

								strBuffer_tLogRow_2.append("|");

								if (row5.genres != null) { //

									strBuffer_tLogRow_2.append(String.valueOf(row5.genres));

								} //

								strBuffer_tLogRow_2.append("|");

								if (row5.errorCode != null) { //

									strBuffer_tLogRow_2.append(String.valueOf(row5.errorCode));

								} //

								strBuffer_tLogRow_2.append("|");

								if (row5.errorMessage != null) { //

									strBuffer_tLogRow_2.append(String.valueOf(row5.errorMessage));

								} //

								if (globalMap.get("tLogRow_CONSOLE") != null) {
									consoleOut_tLogRow_2 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
								} else {
									consoleOut_tLogRow_2 = new java.io.PrintStream(
											new java.io.BufferedOutputStream(System.out));
									globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_2);
								}
								consoleOut_tLogRow_2.println(strBuffer_tLogRow_2.toString());
								consoleOut_tLogRow_2.flush();
								nb_line_tLogRow_2++;
//////

//////                    

///////////////////////    			

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
								 * [tLogRow_2 process_data_end ] start
								 */

								currentComponent = "tLogRow_2";

								/**
								 * [tLogRow_2 process_data_end ] stop
								 */

								/**
								 * [tLogRow_3 process_data_end ] start
								 */

								currentComponent = "tLogRow_3";

								/**
								 * [tLogRow_3 process_data_end ] stop
								 */

							} // End of branch "row4"

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
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						nb_line_tFileInputDelimited_1++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_1 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_1 != null) {
							csvReadertFileInputDelimited_1.close();
						}
					}
					if (csvReadertFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", nb_line_tFileInputDelimited_1);
					}

				}

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
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
					runStat.updateStat(resourceMap, iterateId, 2, 0, "year_valid");
				}

				ok_Hash.put("tMap_2", true);
				end_Hash.put("tMap_2", System.currentTimeMillis());

				/**
				 * [tMap_2 end ] stop
				 */

				/**
				 * [tFlowMeter_9 end ] start
				 */

				currentComponent = "tFlowMeter_9";

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "split_genre");
				}

				ok_Hash.put("tFlowMeter_9", true);
				end_Hash.put("tFlowMeter_9", System.currentTimeMillis());

				/**
				 * [tFlowMeter_9 end ] stop
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
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row6");
				}

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

				/**
				 * [tLogRow_3 end ] start
				 */

				currentComponent = "tLogRow_3";

//////

				java.io.PrintStream consoleOut_tLogRow_3 = null;
				if (globalMap.get("tLogRow_CONSOLE") != null) {
					consoleOut_tLogRow_3 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
				} else {
					consoleOut_tLogRow_3 = new java.io.PrintStream(new java.io.BufferedOutputStream(System.out));
					globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_3);
				}

				consoleOut_tLogRow_3.println(util_tLogRow_3.format().toString());
				consoleOut_tLogRow_3.flush();
//////
				globalMap.put("tLogRow_3_NB_LINE", nb_line_tLogRow_3);

///////////////////////    			

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row4");
				}

				ok_Hash.put("tLogRow_3", true);
				end_Hash.put("tLogRow_3", System.currentTimeMillis());

				/**
				 * [tLogRow_3 end ] stop
				 */

				/**
				 * [tLogRow_2 end ] start
				 */

				currentComponent = "tLogRow_2";

//////
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
				 * [tFlowMeter_9 finally ] start
				 */

				currentComponent = "tFlowMeter_9";

				/**
				 * [tFlowMeter_9 finally ] stop
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
				 * [tLogRow_3 finally ] start
				 */

				currentComponent = "tLogRow_3";

				/**
				 * [tLogRow_3 finally ] stop
				 */

				/**
				 * [tLogRow_2 finally ] start
				 */

				currentComponent = "tLogRow_2";

				/**
				 * [tLogRow_2 finally ] stop
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
		final DimMovie DimMovieClass = new DimMovie();

		int exitCode = DimMovieClass.runJobInTOS(args);

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
			java.io.InputStream inContext = DimMovie.class.getClassLoader()
					.getResourceAsStream("pipline_complite/dimmovie_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = DimMovie.class.getClassLoader()
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
					context.setContextType("ENV", "id_String");
					if (context.getStringValue("ENV") == null) {
						context.ENV = null;
					} else {
						context.ENV = (String) context.getProperty("ENV");
					}
					context.setContextType("input_csv", "id_String");
					if (context.getStringValue("input_csv") == null) {
						context.input_csv = null;
					} else {
						context.input_csv = (String) context.getProperty("input_csv");
					}
					context.setContextType("output_csv", "id_String");
					if (context.getStringValue("output_csv") == null) {
						context.output_csv = null;
					} else {
						context.output_csv = (String) context.getProperty("output_csv");
					}
					context.setContextType("input_file", "id_String");
					if (context.getStringValue("input_file") == null) {
						context.input_file = null;
					} else {
						context.input_file = (String) context.getProperty("input_file");
					}
					context.setContextType("output_file", "id_String");
					if (context.getStringValue("output_file") == null) {
						context.output_file = null;
					} else {
						context.output_file = (String) context.getProperty("output_file");
					}
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
			if (parentContextMap.containsKey("ENV")) {
				context.ENV = (String) parentContextMap.get("ENV");
			}
			if (parentContextMap.containsKey("input_csv")) {
				context.input_csv = (String) parentContextMap.get("input_csv");
			}
			if (parentContextMap.containsKey("output_csv")) {
				context.output_csv = (String) parentContextMap.get("output_csv");
			}
			if (parentContextMap.containsKey("input_file")) {
				context.input_file = (String) parentContextMap.get("input_file");
			}
			if (parentContextMap.containsKey("output_file")) {
				context.output_file = (String) parentContextMap.get("output_file");
			}
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

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : DimMovie");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
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
 * 150048 characters generated by Talend Open Studio for Data Integration on the
 * 9 janvier 2025, 20:31:50 CET
 ************************************************************************************************/