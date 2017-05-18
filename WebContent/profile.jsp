<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="voting.ElectionBean, java.util.*, voting.UserBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Election Page</title>
</head>
<body>
<form action="/voting/VotingController" method="post">
<% 
	ElectionBean eBean = (ElectionBean) session.getAttribute("currentElection");
	UserBean uBean = (UserBean) session.getAttribute("currentUser");
	boolean noElection = eBean.getNoElection();
	if(!noElection)
	{
		HashMap<String, ArrayList<String>> positionCandidateMap = eBean.getPositionCandidateMap();
		
		Set set = positionCandidateMap.keySet();
		Iterator itr = set.iterator();
		while(itr.hasNext())
		{
			String positionName = (String)itr.next();
			ArrayList<String> candidateList = positionCandidateMap.get(positionName);
	%>
	<label style="align:center; font-weight:bold;"><%=positionName %></label><br>
	<%
			for(String candidate: candidateList)
			{
				String[] candidateInfo = candidate.split(":");
	%>
	<input type="radio" name="<%=positionName%>" value="<%=candidateInfo[0]%>"><%=candidateInfo[1] %><br>
	<%
			}
		}
	%>
	<input type="hidden" name="electionID" value="<%=(int)eBean.getElectionId() %>">
	<input type="hidden" name="member_id" value="<%=(int)uBean.getMember_id() %>">
	<input type="submit">
	</form>
	<%
	}
	else
	{
		%>
<h1>No Election in Progress! Come back later....</h1>
		<%
	}
%>
</body>
</html>