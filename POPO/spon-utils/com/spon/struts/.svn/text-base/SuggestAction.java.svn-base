package com.spon.struts;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.spon.utils.util.BA_TOOLS;

public class SuggestAction extends DispatchAction {

	public ActionForward getSuggest(ActionMapping in_mapping, ActionForm in_form, HttpServletRequest in_request, HttpServletResponse in_response) throws Exception {
		String encoding = in_request.getParameter("enc");
		in_response.addHeader("Content-Type", "text/xml;charset=" + encoding + "");

		String word = in_request.getParameter("word");
		String col = in_request.getParameter("col");
		String from = in_request.getParameter("from");

		
		if(!word.equals("null"))
		{
		DataSource ds;
		
		ArrayList collection = new ArrayList();
		
		//ds = getDataSource(in_request, "SPOS");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			//conn = ds.getConnection();
			conn = tools.getConnection();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select distinct "+col+" from "+from+" where "+col+" like '" + word + "%'  order by "+col+" ");
			while (rs.next()) {
				collection.add(rs.getString(1));
			}
			rs.close();
			stmt.close();

		} catch (SQLException e2) {
			e2.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Start to build the suggestions list
		ArrayList suggestions = new ArrayList();

		if (word != null && word.length() > 0) {
			Iterator iter = collection.iterator();

			while (iter.hasNext()) {
				String currentWord = (String) iter.next();

				if (currentWord.toLowerCase().startsWith(word.toLowerCase()))
					suggestions.add(currentWord);
			}
		}

		
		OutputStream os= in_response.getOutputStream();
		os.write(getXMLSuggestionList(suggestions, encoding).getBytes("UTF-8"));
		
		
		}
		return null;

	}

	

	protected String getXMLSuggestionList(Collection suggestions, String in_encoding) {

		StringBuffer res = new StringBuffer("<?xml version=\"1.0\" encoding=\"").append(in_encoding).append("\" ?>\n");

		res.append("<results>");
		if (suggestions != null) {
			Iterator iter = suggestions.iterator();
			while (iter.hasNext()) {
				String currentWord = (String) iter.next();
				res.append("<result value=\"").append(currentWord).append("\" />");
			}
		}

		res.append("</results>");

		return res.toString();

	}
	
	public ActionForward getCodeName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  {
	
		String table=request.getParameter("table");
		String col=request.getParameter("col");
		String key="";
		try{
			key=request.getParameter("key");
		}catch(Exception E) {
		}
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String keyvalue=request.getParameter("keyvalue")==null?"":request.getParameter("keyvalue");
		String condition=request.getParameter("condition");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		String msg="";
		
		//DataSource ds = getDataSource(request, "SPOS");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		StringBuffer sb=new StringBuffer();
		try {
			//conn = ds.getConnection();
			conn = tools.getConnection();
			
			Statement stmt=conn.createStatement();
			
			
			sb.append(" select ");
			sb.append(col);
			sb.append(" from ");
			sb.append(table);
			sb.append(" where ");
			sb.append(key);
			sb.append("=");
			sb.append("'");
			sb.append(keyvalue.trim());
			sb.append("'");
			
			//String sql="select "+col+" from "+table +" where "+key+"='"+keyvalue+"'";
			if(!condition.trim().equals(""))
			{
				sb.append("  ");
				//sb.append(" and ");
				sb.append(condition.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%"));
			}
			sb.append(" group by " +col+" order by " +col);
			ResultSet rs=stmt.executeQuery(sb.toString());
			int i=0;
			while(rs.next())
			{
				if (i>0)
					msg+="@Z@";
				msg+=rs.getString(1);
				i++;
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		try {
			OutputStream os= response.getOutputStream();
			os.write(msg.getBytes("UTF-8"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public ActionForward getUserName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  {
		
		String table=request.getParameter("table");
		String col=request.getParameter("col");
		String key="";
		try{
			key=request.getParameter("key");
		}catch(Exception E) {
		}
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String keyvalue=request.getParameter("keyvalue")==null?"":request.getParameter("keyvalue");
		String condition=request.getParameter("condition");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		String msg="";
		
		//DataSource ds = getDataSource(request, "SPOS");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		

		StringBuffer sb=new StringBuffer();
		try {
			conn = tools.getConnection();
			Statement stmt=conn.createStatement();
			
			
			sb.append(" select ");
			sb.append(col);
			sb.append(" from ");
			sb.append(table);
			sb.append(" where ");
			sb.append(key);
			sb.append(" like ");
			sb.append("'");
			sb.append(keyvalue.trim());
			sb.append("%'");
			
			//String sql="select "+col+" from "+table +" where "+key+"='"+keyvalue+"'";
			if(!condition.trim().equals(""))
			{
				sb.append("  ");
				//sb.append(" and ");
				sb.append(condition.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%"));
			}
			sb.append(" group by " +col+" " );
			ResultSet rs=stmt.executeQuery(sb.toString());
			int i=0;
			while(rs.next())
			{
				if (i>0)
					msg+="@Z@";
				msg+=rs.getString(1);
				i++;
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		try {
			OutputStream os= response.getOutputStream();
			os.write(msg.getBytes("UTF-8"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
