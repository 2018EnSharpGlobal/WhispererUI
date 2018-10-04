package com.example.gjwls.whisperer.Navigation;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Navigation {

    AStar museum;

    public static int count_row;
    public static int count_col;

    Node initialNode;
    Node finalNode;

    List<Node> path;

    public ArrayList navi_text_List;

    int[] check_work;

    List<Node> work;

    public Navigation() {
        initialNode = new Node(0,0);
        finalNode = new Node(1,9);

        navi_text_List = new ArrayList();
        work = null;
        museum = new AStar(MapInfo.map_rows,MapInfo.map_cols,initialNode,finalNode);
        museum.setInformations(MapInfo.museum);

        Extract_Work extract_work = new Extract_Work();
        work = extract_work.find_Work();

//
//        //확인용으로 해놓은 path
//        path = museum.findPath();

    }

    //가까운 작품
    public void compare_close_Work(){
        check_work = new int[work.size()];

        for(int i=0;i<check_work.length;i++){
            check_work[i] = museum.calculate_F(initialNode,work.get(i));
            Log.e(String.valueOf(i),String.valueOf(check_work[i]));
        }

        int component = compare_Minimum(check_work);

        Node finalNode = work.get(component);

        museum.setFinalNode(finalNode);

        work.remove(component);

    }

    //노드가 가는 경로가 잘 가고 있는지 체크하기
    public boolean user_CheckPosition(List<Node> path,int user_row,int user_col){
        boolean check_Position = false;
        for(Node node : path) {
            if (node.getRow() - 1 >= 0 &&
                    node.getCol() - 1 >= 0 &&
                    node.getRow() + 1 <= MapInfo.map_rows &&
                    node.getCol() + 1 <= MapInfo.map_cols) {
                if (node.getRow() - 1 <= user_row ||
                        user_row <= node.getRow() + 1 ||
                        node.getCol() - 1 <= user_col ||
                        user_col <= node.getCol() + 1) {
                    check_Position = true;
                }
            }
        }
        if(check_Position){
            return true;
        }
        else{
            return false;
        }
    }

    //배열에서 최소의 F 값을 찾아 그 요소 성분을 반환
    public  int compare_Minimum(int[] means_transportation){
        int find_Minimum = means_transportation[0];
        for(int i=1;i<means_transportation.length;i++){
            if(find_Minimum >= means_transportation[i]){
                find_Minimum = means_transportation[i];
            }
        }

        int find_component;

        for(find_component=0;find_component<means_transportation.length;find_component++){
            if(find_Minimum == means_transportation[find_component])
                break;
        }

        return find_component;
    }

    public Node get_currentNode(double user_latitude,double user_longitude){

        Node current_Node = null;
        user_latitude -= 35.84496657;
        user_longitude -= 127.13117956 ;

        double cos = 0.97927068334533469037195650759799;
        double sin = 0.20255598914957127150186025821102;

        user_latitude = cos * user_latitude + sin * user_longitude;
        user_longitude = -sin * user_latitude + cos * user_longitude;

        int count_row = (int)(user_latitude / 0.00002);
        int count_col=(int)(user_longitude / 0.00002);

        //return String.valueOf(count_row)+","+String.valueOf(count_col);

        current_Node = new Node(count_row,count_col);

        if(count_row < 0 || count_col <0){
            return null;
        }
        else{
            return current_Node;
        }
    }

    //
    public String get_Device_bearing(double bearing){
        if(bearing >= MapInfo.up_bearing -45 && bearing <= MapInfo.up_bearing + 45){
            return "앞";
        }
        else if(bearing >= MapInfo.left_bearing -45 && bearing <= MapInfo.left_bearing +45){
            return "왼쪽";
        }
        else if(bearing >= MapInfo.down_bearing -45 && bearing <= MapInfo.down_bearing +45){
            return "아래쪽";
        }
        else{
            return "오른쪽";
        }
    }

    //위쪽 방향 디바이스랑 비교해서 참,거짓으로 반환
    public boolean check_down_bearing(double bearing){
        if(bearing >= MapInfo.down_bearing -45 && bearing <= MapInfo.down_bearing + 45){
            return true;
        }
        else{
            return false;
        }
    }

    //위쪽 방향 디바이스랑 비교해서 참,거짓으로 반환
    public boolean check_right_bearing(double bearing){
        if(bearing >= MapInfo.up_bearing -45 && bearing <= MapInfo.up_bearing + 45){
            return true;
        }
        else{
            return false;
        }
    }

    //위쪽 방향 디바이스랑 비교해서 참,거짓으로 반환
    public boolean check_left_bearing(double bearing){
        if(bearing >= MapInfo.up_bearing -45 && bearing <= MapInfo.up_bearing + 45){
            return true;
        }
        else{
            return false;
        }
    }

    //위쪽 방향 디바이스랑 비교해서 참,거짓으로 반환
    public boolean check_up_bearing(double bearing){
        if(bearing >= MapInfo.up_bearing -45 && bearing <= MapInfo.up_bearing + 45){
            return true;
        }
        else{
            return false;
        }
    }


    public void Navi_Path(){
        int i=0;

        for(i=0;i<path.size()-1; i++){
            int change_row = 0;
            int change_col = 0;

            String navi_text = "";

            change_row = path.get(i+1).getRow() - path.get(i).getRow();
            change_col = path.get(i+1).getCol() - path.get(i).getCol();

            if(change_row == 1 && change_col ==0){
                navi_text= "아래쪽";
            }
            else if(change_row == -1 && change_col ==0){
                navi_text= "위쪽";
            }
            else if(change_row == 0 && change_col == 1){
                navi_text= "오른쪽";
            }
            else if(change_row == 0 && change_col == -1){
                navi_text= "왼쪽";
            }
            else if(change_row == 1 && change_col ==1){
                navi_text= "오른쪽아래";
            }
            else if(change_row == 1 && change_col ==-1){
                navi_text= "왼쪽아래";
            }
            else if(change_row ==-1 && change_col ==1){
                navi_text= "오른쪽위";
            }
            else if(change_row ==-1 && change_col ==-1){
                navi_text= "왼쪽위";
            }
            else{
                navi_text = "이동수단";
            }

            navi_text_List.add(navi_text);
        }
    }

    public String outPut_Navi(double device_bearing){
        Navi_Path();

        String current_text =(String)navi_text_List.get(0);
        String return_value;
        int count = 1 ;

        List<Node> one_path = new ArrayList<>();

        while((String)navi_text_List.get(count)== current_text && count != navi_text_List.size()-1){
            count++;
        }

        for(int j=0;j<count;j++){
            navi_text_List.remove(0);
        }
        for(int k=0;k<count-1;k++){
            one_path.add(path.get(0));
            path.remove(0);
        }
        one_path.add(path.get(0));

        double destination_bearing = 0;

        switch(current_text){
            case "아래쪽":
                destination_bearing = MapInfo.down_bearing;
                break;
            case "위쪽":
                destination_bearing = MapInfo.up_bearing;
                break;
            case "오른쪽":
                destination_bearing = MapInfo.right_bearing;
                break;
            case "왼쪽":
                destination_bearing = MapInfo.left_bearing;
                break;
            case "오른쪽아래":
                destination_bearing = MapInfo.right_down_bearing;
                break;
            case "왼쪽아래":
                destination_bearing = MapInfo.left_down_bearing;
                break;
            case "오른쪽위":
                destination_bearing = MapInfo.right_up_bearing;
                break;
            case "왼쪽위":
                destination_bearing = MapInfo.left_up_bearing;
                break;
        }

        Log.e("방향",String.valueOf(destination_bearing));

        return_value = String.valueOf(destination_bearing)+","+String.valueOf(count*MapInfo.node_space+"m");
        return return_value;
    }

    //방향의 마지막 노드랑 사용자 위치를 비교해서 맞으면 outPut_Navi 함수 실행
    public boolean get_navi_final_node(double latitude, double longitude,double device_bearing){
        Node current_Node = get_currentNode(latitude, longitude);
        String navi_text;

        if(current_Node.getRow() == path.get(0).getRow() && current_Node.getCol() == path.get(0).getCol()){
            path.remove(0);
            return true;
        }
        else{
            return false;
        }
    }

    public AStar get_AStar(){
        return museum;
    }

    public void set_Path(List<Node> path){
        this.path = path;
    }
}
