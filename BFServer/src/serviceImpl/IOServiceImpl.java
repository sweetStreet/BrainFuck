package serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

import service.IOService;

public class IOServiceImpl implements IOService{
	//用于保存用户文件的文件夹位置
	public final static String FOLDER="userFile"; 
	public final static String VERSION_FOLDER="versionFolder"; 
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		boolean isSuccess=false;
		String newfilename=FOLDER+"//"+userId +"_"+fileName+".txt";
		File f = new File(newfilename);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			String name=VERSION_FOLDER+"//"+userId +"_"+fileName+"_"+f.lastModified()+".txt";
			File oldversionFile=new File(name);
			try {
				oldversionFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String result="";
				try{
					BufferedReader br=new BufferedReader(new FileReader(f));
					String s="";
					while((s=br.readLine())!=null){
						result=result+s;
					}
					br.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			
				if(result.equals(file)){
					oldversionFile.delete();
					isSuccess=false;
				}else{
					try {
						BufferedWriter bw=new BufferedWriter(new FileWriter(oldversionFile));
						bw.write(result);
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
				try {
					BufferedWriter bw=new BufferedWriter(new FileWriter(f));
					bw.write(file);
					bw.close();
					isSuccess=true;
				} catch (IOException e) {
					e.printStackTrace();
					isSuccess=false;
				}
			}
		return isSuccess;
	}
	
	
	@Override
	public String readFile(String userId, String fileName) {
		File f=new File(FOLDER+"//"+userId + "_" + fileName);
		String result="";
		try{
			BufferedReader br=new BufferedReader(new FileReader(f));
			String s="";
			while((s=br.readLine())!=null){
				result=result+s;
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<String> readFileList(String userId) {
		File file=new File(FOLDER);
		File[] files=file.listFiles();
		ArrayList<String> list=new ArrayList<String>();
 		for(int i=0;i<files.length;i++){
 			String[] temp=files[i].getName().split("_");
 			if(temp[0].equals(userId)){
 				list.add(temp[1]);
 			}
 		}
		return list;
	}

	@Override
	public ArrayList<String> readVersionFileList(String userId,String presentFile) {
		File file=new File(VERSION_FOLDER);
		File[] files=file.listFiles();
		ArrayList<String> list=new ArrayList<String>();
 		for(int i=0;i<files.length;i++){
 			String[] temp=files[i].getName().split("_");
 			if(temp[0].equals(userId)&&temp[1].equals(presentFile)){
 				list.add(temp[2]);
 			}
 		}
		return list;
	}


	@Override
	public String readVersionFile(String userId, String presentFile, String version) throws RemoteException {
		File f=new File(VERSION_FOLDER+"//"+userId +"_"+presentFile+"_"+version);
		String result="";
		try{
			BufferedReader br=new BufferedReader(new FileReader(f));
			String s="";
			while((s=br.readLine())!=null){
				result=result+s;
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	
}
