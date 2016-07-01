package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService,ExecuteService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	private ExecuteService executeService;
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
		executeService=new ExecuteServiceImpl();
	}

	@Override
	public boolean writeFile(String file, String userId, String fileName) throws RemoteException{
		return iOService.writeFile(file, userId, fileName);
	}

	@Override
	public String readFile(String userId, String fileName) throws RemoteException{
		return iOService.readFile(userId, fileName);
	}

	@Override
	public ArrayList<String>  readFileList(String userId) throws RemoteException{
		return iOService.readFileList(userId);
	}
	
	
	@Override
	public boolean login(String username, String password) throws RemoteException {
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return userService.logout(username);
	}

	@Override 
	public void dologout(String username) throws RemoteException{
	}
	
	@Override
	public boolean signup(String username, String password) throws RemoteException {
		return userService.signup(username, password);
	}

	@Override
	public String execute(String code, String param) throws RemoteException {
		return executeService.execute(code, param);
	}

	@Override
	public String readVersionFile(String userId, String presentFile, String version) throws RemoteException {
		return iOService.readVersionFile(userId, presentFile,version);
	}

	@Override
	public ArrayList<String> readVersionFileList(String userId, String presentFile) throws RemoteException {
		return iOService.readVersionFileList(userId, presentFile);
	}

}
