//服务器IOService的Stub，内容相�?
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public String readVersionFile(String userId, String presentFile, String version)throws RemoteException;
	
	public ArrayList<String> readFileList(String userId)throws RemoteException;
	
	public ArrayList<String>readVersionFileList(String userId,String presnetFile)throws RemoteException;
}
