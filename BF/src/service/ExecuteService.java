package service;

import java.rmi.RemoteException;

public interface ExecuteService {
	public String execute(String code, String param) throws RemoteException;
}
