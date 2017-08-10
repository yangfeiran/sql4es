package nl.anchormen.esjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import nl.anchormen.sql4es.ESResultSet;

public class CC {

	public static void main(String[] args) throws Exception {
		/*Map<String, Object> map = new HashMap<String, Object>();
    	map.put("fulltext", "成都99KM东西南北");
    	map.put("_id", 21);
    	List<Map<String, Object>> list   = new ArrayList<Map<String,Object>>();
    	list.add(map);
    	new SQL4ESUtil("scgaedb", "10.65.6.143", 8300).bulkRequest("test2", "cc", list);*/
    	/*
		String imagePath = "E:/QQ图片20161221143147.png";
		String outFile = "E:/thumb_QQ图片20161221143147.png";
		ImageUtil.aliImage(imagePath, outFile);
		*/
    	q();
		
	}

	private static void q() throws Exception {
		Class.forName("nl.anchormen.sql4es.jdbc.ESDriver");
		Properties info = new Properties();
		//info.setProperty("cluster.name", "elasticsearch");
		info.setProperty("fetch.size", "10");
		info.setProperty("scroll.timeout.sec", "100");
		info.setProperty("result.nested.lateral", "true");
//		info.setProperty("es.hosts", "10.65.6.143:8300,10.65.6.146:8300,10.65.6.144:8300,10.65.6.145:8300,10.65.6.148:8300,10.65.6.150:8300");
		String url = "jdbc:sql4es://172.16.50.21:9300/lagou3?cluster.name=elasticsearch";
		//url = "jdbc:sql4es://172.16.50.21:7300/test1?cluster.name=gses";
		Connection con = DriverManager.getConnection(url,info);
		Statement st = con.createStatement();
		
		String sql="select companyFullName,jobs.positionId,jobs.positionName from company where _id='3024'";
		show(st, sql);
		con.close();
	}
	
	public static void show(Statement st, String sql) throws SQLException {
		ResultSet rs = st.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int nrCols = rsmd.getColumnCount();
	
		long index=0;
		System.out.println("result count:"+((ESResultSet)rs).getTotal());
		//while (rs !=null){
			while (rs.next()) {
				for (int i = 1; i <= nrCols; i++) {
					System.out.print(rs.getObject(i) + "\t");
				}
				System.out.println();
				index++;
			}
			/*rs.close();
			boolean moreResults = st.getMoreResults();
			if(moreResults)
				rs = st.getResultSet();
			else
				break;
		}*/
		System.out.println("total:"+index);
		rs.close();
	}

}
