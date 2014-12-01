package folderindexer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String pathFolder = "20_newsgroups";
        String pathFolderIndex="20_newsgroups\\index.txt";
        File folder = new File(pathFolder);
        Scanner s = new Scanner(System.in);
        FolderIndexer fi = new FolderIndexer();
        
        int exit=0;
        
        fi.welcome();

        if(!folder.exists()){
            System.out.println("Folder yang akan diindex tidak ada , copy Folder 20_newsgroups ke dalam folder Program.");
            System.exit(1);
        }
        
        if(!fi.folderIniSudahDiIndex()){
            System.out.println("File sedang di index... . . . . . . . .");
            FileWriter fw = new FileWriter(pathFolderIndex,true);
            fi.indexerAll(folder, fw); 
            fw.close();
        }
        
        fi.insertDataToTree();
        
        while(exit==0){

            System.out.println("Program info : ** Untuk exit , ketik 00 ** ");
            System.out.print("Cari kata di dalam folder : ");
            String wordToFind=s.nextLine();
            if(wordToFind.equals("00")){
                exit=1;
            }else{
                fi.traversePreOrder(wordToFind);
            }
        }
        
    }
}
