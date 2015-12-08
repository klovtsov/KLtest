import com.edmz.api.APIClient
import com.edmz.api.APICommandLib
import com.edmz.api.bo.CodeMessageResult
import com.sshtools.j2ssh.session.SessionChannelClient
import com.sshtools.j2ssh.util.InvalidStateException

cwd = new File( "." ).getCanonicalPath()
keydir = new File(cwd).getParent() + "\\keys"

params = [:]
args.each { param = it.split('=')
	params.put(param[0], param[1])
}
params['authFile'] = keydir + '\\' + params['authFile']

APIClient ac = new APIClient()
//Connect to TPAM
try{
	ac.connect(params['TPAM'])
	ac.authenticate(params['authFile'],params['apiUser'])
}
catch (e) {
	println("Something wrong with connection to TPAM: " + e)
}

try{
	SessionChannelClient scc = ac.createSessionChannel()
	APICommandLib acl = new APICommandLib(scc)
	CodeMessageResult res = new CodeMessageResult()
	//Execute command
	acl.manualPasswordReset(params['PasswordID'], params['Status'], res)
	System.out.println(res.getMessage())
	
}catch (IOException e) {

println(e)

} catch (InvalidStateException e) {

println(e)

} catch (InterruptedException e) {

println(e)

} finally {
	ac.disconnect()
}
