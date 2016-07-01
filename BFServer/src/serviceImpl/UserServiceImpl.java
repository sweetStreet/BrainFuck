package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import service.UserService;

public class UserServiceImpl implements UserService{
	public static final String FILEPATH="adminInformation.txt";
	
	ArrayList<String> list=new ArrayList<String>();
	@Override
	public boolean signup(String username,String password) throws RemoteException{
		if(judgeSignup(username)==false){
			return false;
		}
		else{
			writeAdminInformation(username,password);
			return true;
	}
	}
	@Override
	public boolean login(String username, String password) throws RemoteException {
		if(judgeLogin(username,password)==true){
				list.add(username);
				return true;
			}
			else{
			return false;
			}
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		boolean result=false;
		if(list.contains(username)){
			result=true;
		}
		return result;
	}
	
	@Override
	public void dologout(String username) throws RemoteException {
		list.remove(username);
	}
	
	public boolean judgeSignup(String username){
		File file=new File(FILEPATH);
		String s=null;
		boolean result=true;
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			while((s=br.readLine())!=null){
			String[] info=s.split(",",-1);
			if(info[0].equals(username)){
				result=false;
				break;
			}
			}br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean judgeLogin(String username,String password){
		File file=new File(FILEPATH);
		String s=null;
		boolean result=false;
		try {
			BufferedReader br=new BufferedReader(new FileReader(file));
			while((s=br.readLine())!=null){
			String[] info=s.split(",",-1);
			if(info[0].equals(username)){
				if(info[1].equals(password)){
					result=true;
				}
			}
			}br.close();
		} catch (IOException e) {
			result=false;
		}
		return result;
	}
	
	public void writeAdminInformation(String username,String password){
		File file=new File(FILEPATH);
		try{
		BufferedWriter bw=new BufferedWriter(new FileWriter(file,true));
		bw.write(username+","+password+"\r\n");
		bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}