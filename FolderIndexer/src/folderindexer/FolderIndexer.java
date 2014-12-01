package folderindexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FolderIndexer {
    String pathFolder = "20_newsgroups";
    String pathFolderIndex = "20_newsgroups\\index.txt";
    Data root;
    int numberOfFileContaindWordToFind=0;
    
    public void welcome(){
        System.out.println("Selamat datang di program Folder Indexer");
        System.out.println("    Oleh Kelompok 9 | Kelas SI-37-01");
        System.out.println("             Sistem Informasi");
        System.out.println("        Fakultas Rekayasa Industri");
        System.out.println("           -=Telkom University=-");
        System.out.println();
        System.out.println("Folder yang sedang di index adalah : "+pathFolder);
        System.out.println();
    }
    
    boolean folderIniSudahDiIndex(){
        File toCheck = new File(pathFolderIndex);
        boolean sudah=false;
        if(toCheck.exists()){
            sudah=true;
        }
        return sudah;
    }
    
    private boolean isEmpty(){
        return(root==null);
    }
    
    public void indexerAll(File folder,FileWriter fw) throws IOException{
        if(folder.exists()){
            File[] array = folder.listFiles();
            
            for(int i=0; i<array.length; i++){
                File temp = array[i];
                
                if(temp.isFile()){
                    if(!temp.getName().equals("index.txt")){
                        fw.write(temp.getPath());
                        fw.write("\r\n");
                    }
                }else{
                    indexerAll(temp,fw);
                }
            }
        }
    }
    
    public void insertDataToTree() throws FileNotFoundException, IOException{
        
        FileReader fr=new FileReader(pathFolderIndex);
        BufferedReader br = new BufferedReader(fr);
        
        String line = br.readLine();
        
        while(line!=null){
            File filename = new File(line);
            Data tmp = new Data();
            tmp.path = line;
            tmp.id = new Integer(filename.getName());
            
            if(isEmpty()){
                root = tmp;
            }else{
                Data current = root;
                Data parent = null;
            
                boolean left = true;
            
                while(current!=null){
                    parent=current;
                
                    if(current.id<tmp.id){
                        current = current.right;
                        left = false;
                    }else{
                        current = current.left;
                        left = true;
                    }
                }
            
                if(left){
                    parent.left = tmp;
                }else{
                    parent.right = tmp;
                }
            }
        line=br.readLine();
            
        }
        fr.close();
        br.close();
    }
    
    public void traversePreOrder(String wordToFind) throws IOException{
        preOrder(root,wordToFind);
        System.out.println();
        if(numberOfFileContaindWordToFind==0){
            System.out.println("Tidak ditemukan File yang mengandung kata "+wordToFind);
        }else{
            System.out.println("Jumlah File yang mengandung kata "+wordToFind+" = "+numberOfFileContaindWordToFind);
        }        
        System.out.println();
        numberOfFileContaindWordToFind=0;
    }
    
    private void preOrder(Data root,String wordToFind) throws IOException{
        if(root!=null){
            prosesFile(root.path,wordToFind);
            preOrder(root.left,wordToFind);
            preOrder(root.right,wordToFind);
        }
    }
    
    private void prosesFile(String path,String wordToFind) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        int lineNumber = 1;
        boolean found = false;
        
        String line = br.readLine();
        
        while(line!=null){
            int index = line.indexOf(wordToFind);
            if(index > -1){
                if(found==false){
                    System.out.println("Data ditemukan di : " + path);
                    found = true;
                    numberOfFileContaindWordToFind++;
                }
                System.out.println("\tLine-" + lineNumber + "-->" + line);
            }
            line=br.readLine();
            lineNumber++;
        }
        fr.close();
        br.close();
    }

}
