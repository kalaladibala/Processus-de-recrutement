import com.bonitasoft.engine.api.APIAccessor
import com.bonitasoft.engine.api.ProcessAPI
import java.util.logging.Logger
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance
import org.bonitasoft.engine.bpm.process.ProcessInstance

static def getEmailAssignee (long processInstanceId, APIAccessor apiAccessor) {
	
	Logger logger = Logger.getLogger("Mes Scripts")
	logger.info("get Email assignee start")
	
	//import apis 
	ProcessAPI processApi = apiAccessor.getProcessAPI();
	IdentityAPI identityApi = apiAccessor.getIdentityAPI();
	
	//Get the TaskInstance
	HumanTaskInstance humanTaskInstance = processApi.getHumanTaskInstances(processInstanceId, "Besoin émis", 0, 1).get(0);
	
	
	//Get the user id assigned to the task Besoin émis;
	long besoinEmisAssignedId = humanTaskInstance.getAssigneeId();
	
	//Get the Besoin émis email
	String besoinEmisAssignedEmail = "";
	if(besoinEmisAssignedId > 0) {
		besoinEmisAssignedEmail = identityApi.getUserContactData(besoinEmisAssignedId, false).getEmail();
	}
	else {
		besoinEmisAssignedEmail = "pbeguier@bluexml.com"
	}
	return besoinEmisAssignedEmail;
}

static def getEmailInitiator (long processInstanceId, APIAccessor apiAccessor) {
	
	//import the apis
	ProcessAPI processApi = apiAccessor.getProcessAPI();
	IdentityAPI identityApi = apiAccessor.getIdentityAPI();
	
	//Get the ProcessInstance
	ProcessInstance processInstance = processApi.getProcessInstance(processInstanceId);
	
	//Get the initiator id of the case
	long initiator = processInstance.getStartedBy();
	
	//Get the initiator Email
	String initiatorEmail = identityApi.getUserContactData(initiator, false).getEmail();
	return initiatorEmail;
}






