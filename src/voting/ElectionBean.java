package voting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ElectionBean implements Serializable{
	
	private int electionId;
	private HashMap<String, ArrayList<String>> positionCandidateMap = new HashMap<String, ArrayList<String>>();
	private boolean noElection = false;
	
	public int getElectionId() {
		return electionId;
	}

	public void setElectionId(int electionId) {
		this.electionId = electionId;
	}

	public boolean getNoElection() {
		return noElection;
	}
	
	public void setNoElection(boolean noElection) {
		this.noElection = noElection;
	}
	
	public HashMap<String, ArrayList<String>> getPositionCandidateMap() {
		return positionCandidateMap;
	}

	public void setPositionCandidateMap(HashMap<String, ArrayList<String>> positionCandidateMap) {
		this.positionCandidateMap = positionCandidateMap;
	}
	
}
