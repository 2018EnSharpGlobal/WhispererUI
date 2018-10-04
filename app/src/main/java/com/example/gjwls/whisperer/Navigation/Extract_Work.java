package com.example.gjwls.whisperer.Navigation;

import com.example.gjwls.whisperer.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Extract_Work {

    List<Node> preferred_Node;


    public Extract_Work(){
        preferred_Node = new ArrayList<Node>();
    }

    public List<Node> find_Work(){
        for(int i = 0; i< MainActivity.work_List.size(); i++){
            Node work_Node = null;
            switch ((String)MainActivity.work_List.get(i)){
                case "egypt":
                    work_Node = new Node(0,0);
                    break;
                case "korea":
                    work_Node = new Node(0,8);
                    break;
                case "usa":
                    work_Node = new Node(0,16);
                    break;
                case "Work_4":
                    work_Node = new Node(2,7);
                    break;
                case "Work_5":
                    work_Node = new Node(3,0);
                    break;
                case "Work_6":
                    work_Node = new Node(3,11);
                    break;
                case "Work_7":
                    work_Node = new Node(3,18);
                    break;
                    default:
                        break;
            }
            preferred_Node.add(work_Node);
        }
        return preferred_Node;
    }
}
