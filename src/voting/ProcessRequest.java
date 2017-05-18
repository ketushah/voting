package voting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ProcessRequest {
	
	Connection conn;
	public ProcessRequest(){}
	public ProcessRequest(Connection conn)
	{
		System.out.println(this.getClass().getSimpleName()+" Debug1: Constructor Called ");
		this.conn = conn;
	}
	
	public ElectionBean processing() throws Exception
	{
		ElectionBean eBean;
		Statement st = conn.createStatement();
		ResultSet reSet = st.executeQuery("select ELECTION_ID from election where STATUS=1");
		HashMap<String, ArrayList<String>> candidateMap = new HashMap<String, ArrayList<String>>();
		if(reSet.next())
		{
			boolean electionFlag = true;
			int electionId = reSet.getInt("ELECTION_ID");
			//reSet = st.executeQuery("");
			reSet = st.executeQuery("select POSITION_NAME from election_positions where ELECTION_ID="+electionId);
			while(reSet.next())
			{
				String positionName = reSet.getString("POSITION_NAME");
				candidateMap.put(positionName, new ArrayList<String>());
			}
			Set set = candidateMap.entrySet();
			Iterator itr = set.iterator();
			eBean = new ElectionBean();
			eBean.setElectionId(electionId);
			while(itr.hasNext())
			{
				Map.Entry<String, ArrayList> entry = (Entry<String, ArrayList>) itr.next();
				String positionName = entry.getKey();
				ArrayList<String> candNames = entry.getValue();
				ResultSet rs = st.executeQuery("select MEMBER_ID, FirstName, LastName from voters where MEMBER_ID IN (select CANDIDATE_ID FROM election_candidates WHERE ELECTION_ID="+electionId+" AND POSITION_NAME='"+positionName+"')");
				while(rs.next())
				{
					String candidateName = rs.getString("MEMBER_ID")+":"+rs.getString("FirstName")+" "+rs.getString("LastName");
					candNames.add(candidateName);
					candidateMap.put(positionName, candNames);
				}
				
				eBean.setPositionCandidateMap(candidateMap);
			}
			set = candidateMap.entrySet();
			itr = set.iterator();
			while(itr.hasNext())
			{
				Map.Entry<String, ArrayList> entry = (Entry<String, ArrayList>) itr.next();
				System.out.println(entry.toString());
			}
		}
		else
		{
			eBean = new ElectionBean();
			eBean.setNoElection(true);
			System.out.println(this.getClass().getSimpleName()+" Debug 2: No Election in progress...");
		}
		return eBean;
	}
	
}
