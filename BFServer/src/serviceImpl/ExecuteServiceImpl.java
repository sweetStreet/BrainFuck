//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;

import rmi.DataRemoteObject;
import rmi.RemoteHelper;
import service.ExecuteService;
import service.IOService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService{
	int[] start=new int[8000];//记录 [ 在code中出现的位置
	int[] judge=new int[8000];//记录data数组中要进行是否等于0判断的位置
	byte[] data=new byte[8000];
	int pointer=0;//指针,记录data数组中正在被执行的数的位置
	int count;//指针，记录
	int comma;//记录逗号的个数，确定从数据区读入的字符
	String output;
	@Override
	public String execute(String code, String param) throws RemoteException {		
			output="";
			count=0;
			comma=0;
			char[]input=param.toCharArray();
			for(int i=0;i<code.length();i++){
					switch (code.charAt(i)){
					case '<':
						pointer=pointer-1;
						break;
					case '>':
						pointer=pointer+1;
						break;
					case '+':
						data[pointer]=(byte) (data[pointer]+1);
						break;
					case '-':
						data[pointer]=(byte) (data[pointer]-1);
						break;
					case '[':
						judge[count]=pointer;
						start[count]=i;
						count++;
						break;
					case ']':
						count--;
						if(data[judge[count]]==0){
							break;
						}
						else{
							i=start[count]-1;
							break;
					}
					case ',':
						data[pointer]= (byte)input[comma];
						comma++;
						break;
					case '.':
						dataOutput(data[pointer]);
						break;
					}
				}
					return output;
			}
		
		public  void dataOutput(byte c){
			output=output+String.valueOf((char)c);
		}
	}