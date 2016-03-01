package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
import GUI.StoryBoard.UI.palettePanel;
import GUI.StoryBoard.storyBoard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-16.
 */
public class Layout_Root extends ObjectCustom {

    int buttonNum =1;
    int radiobuttonNum=1;
    int linearlayoutNum=1;
    int imageViewNum=1;
    Point mouse_p;

    private boolean isButton;
    private boolean isRadioButton;
    private boolean isImageView;

    private int x,y;

    public Layout_Root() {

    }
    public Layout_Root(HashMap<String, ObjectCustom> list , JSONObject obj) {
        super();
        objectJObject=obj;
        objectList =list;

        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        addMouseListner();
        makeAllObject(objectJObject);
    }
    public Layout_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan) {
        super();
        panel = pan;
        objectJObject=obj;
        objectList =list;

        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        addMouseListner();

        addMouseactionListner(this.getGraphics());
        makeAllObject(objectJObject);
    }
    public Layout_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist) {
        super();
        JSONArray objectArray;
        panel = pan;
        objectJObject=obj;
        objectList =list;
        nextActivitylist = nextlist;

        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        addMouseListner();

        addMouseactionListner(this.getGraphics());
        makeAllObject(objectJObject);
    }
    public Layout_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist, HashMap<String, Activity> actList) {
        super();
        panel = pan;
        objectJObject=obj;
        objectList =list;
        nextActivitylist = nextlist;
        activityList=actList;

        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        addMouseListner();

        addMouseactionListner(this.getGraphics());
        makeAllObject(objectJObject);
    }
    public Layout_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist, HashMap<String, Activity> actList, storyBoard stroy) {
        super();
        panel = pan;
        objectJObject=obj;
        objectList =list;
        nextActivitylist = nextlist;
        activityList=actList;
        getStroyBoard(stroy);
        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        addMouseListner();

        addMouseactionListner(this.getGraphics());
        makeAllObject(objectJObject);
    }
    public Layout_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist, HashMap<String, Activity> actList, storyBoard stroy, String ActivitName) {
        super(list,obj,pan,nextlist,actList,stroy,ActivitName);
        this.activityName = ActivitName;
        panel = pan;
        objectJObject=obj;
        objectList =list;
        nextActivitylist = nextlist;
        activityList=actList;
        getStroyBoard(stroy);


        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);

        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        addMouseListner();

        addMouseactionListner(this.getGraphics());
        makeAllObject(objectJObject);
    }


    public Layout_Root(String name_, HashMap<String, ObjectCustom> list , JSONObject obj) {
        long width, height, x, y ;
        JSONArray objarr =new JSONArray();
        String name, text, color;


        name = "Layout"+ name_;
        width = Constant.layoutWidth;
        height = Constant.layoutHeight;
        x = 10;
        y = 10;
        color = "gray";

        setId(name);

        setPosition(new Point((int)x, (int)y));
        setObject_height((int)height);
        setObject_width((int)width);
        setColor(color);

        this.setSize((int)width, (int)height);
        this.setLocation((int)x, (int)y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);

        obj.put("name",getId());
        obj.put("x",x);
        obj.put("y",y);
        obj.put("width",width);
        obj.put("height",height);
        obj.put("color",color);
        obj.put("type","layout");
        obj.put("object", objarr );

        setId("layout"+name);
        objectJObject=obj;
        addMouseListner();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isButton){
            g.drawRect(x,y,Constant.buttonWidth,Constant.buttonHeight);
            revalidate();       // 무효화 선언된 화면을 알려줌
            repaint();          // 다시 그려준다.

        }
        else if(isRadioButton){
            g.drawRect(x,y,Constant.buttonWidth,Constant.buttonHeight);
            revalidate();       // 무효화 선언된 화면을 알려줌
            repaint();          // 다시 그려준다.

        }
        else if(isImageView){
            g.drawRect(x,y,Constant.imageVIewWidth,Constant.imageViewHeight);
            revalidate();       // 무효화 선언된 화면을 알려줌
            repaint();          // 다시 그려준다.
        }


    }

    public ObjectCustom CreateObjectCustom(String type, JSONObject jobj){
        if(type.equals("linear layout")){
            Layout_Linear linear = new Layout_Linear(objectList, jobj, panel, nextActivitylist, activityList,  storyboard,activityName);
            linear.getStroyBoard(storyboard);
            return linear;
        }
        else if(type.equals("RelativeLayout")){
            Layout_Relative relative = new Layout_Relative(objectList, jobj, panel, nextActivitylist, activityList,  storyboard,activityName);
            relative.getStroyBoard(storyboard);
            return relative;
        }
        else if(type.equals("Button")){
            Button_Click b = new Button_Click(objectList, jobj,nextActivitylist,activityList,  storyboard,activityName);
            b.getStroyBoard(storyboard);
            return b;
        }
        else if(type.equals("button")){
            Button_Click b = new Button_Click(objectList, jobj,nextActivitylist, activityList,  storyboard,activityName);
            b.getStroyBoard(storyboard);
            return b;
        }
        else if(type.equals("radio button")){
            Button_Radio b= new Button_Radio(objectList, jobj,nextActivitylist, activityList,  storyboard,activityName);
            b.getStroyBoard(storyboard);
            return b;
        }
        else if(type.equals("RadioButton")){
            Button_Radio b= new Button_Radio(objectList, jobj,nextActivitylist, activityList,  storyboard,activityName);
            b.getStroyBoard(storyboard);
            return b;
        }
        else if(type.equals("ImageVIew")){
            Image_View i= new Image_View(objectList, jobj,nextActivitylist, activityList,  storyboard,activityName);
            i.getStroyBoard(storyboard);
            return i;
        }
        else if(type.equals("CheckBox")){
            CheckBox c = new CheckBox(objectList, jobj,nextActivitylist, activityList,  storyboard,activityName);
            return c;
        }
        else if(type.equals("TextView")){
            TextView t = new TextView(objectList, jobj,nextActivitylist, activityList,  storyboard,activityName);
            t.getStroyBoard(storyboard);
            return t;
        }
        else
        {
            ObjectCustom obj = new ObjectCustom();
            obj=null;
            return obj;
        }

    }
    //jobj 는 layout들이 들어있는 obj
    public void makeAllObject(JSONObject jobj){

        JSONArray objectArray;
        objectArray = (JSONArray)jobj.get("object");

        for(int i=0; i<objectArray.size(); i++){
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject)objectArray.get(i);
            if(tempJsonObject.isEmpty())
            {
                objectArray.remove(i);
                i=-1;
            }
        }
        for(int i=0; i<objectArray.size(); i++){
            String type;
            ObjectCustom temp;
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject)objectArray.get(i);

            type =(String)tempJsonObject.get("type");
            temp=CreateObjectCustom(type,tempJsonObject);
            objectList.put((String)tempJsonObject.get("name"),temp);
            add(temp);

        }
    }
    //-----------새로운 버튼 생성---------------
    public void newButton(){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();

        Button_Click b = new Button_Click(""+buttonNum, objectList,tempObj);

        tempArray.add(tempObj);
        add(b);
        objectList.put(""+buttonNum , b);
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        buttonNum++;

    }
    public void newButton(Point point){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();

        Constant.ObjectNew sendfile = new Constant.ObjectNew();
        sendfile.name=""+buttonNum;
        sendfile.objectList=objectList;
        sendfile.jObject=tempObj;
        sendfile.mousep=point;
        sendfile.parentHeight=getObject_height();
        sendfile.parentWidth=getObject_width();
        Button_Click b = new Button_Click(sendfile);

        tempArray.add(tempObj);

        add(b);
        objectList.put(""+buttonNum , b);
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        buttonNum++;

    }
    //-----------새로운 Radio 버튼 생성---------
    public void newRadioButton(){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();

        Button_Radio b = new Button_Radio(""+radiobuttonNum, objectList,tempObj);

        tempArray.add(tempObj);

        add(b);

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        radiobuttonNum++;
    }
    public void newRadioButton(Point point){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();

        Button_Radio b = new Button_Radio(""+radiobuttonNum, objectList,tempObj, point);

        tempArray.add(tempObj);

        add(b);

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        radiobuttonNum++;
    }
    public void newImageView(Point point){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();

        Image_View b = new Image_View(""+imageViewNum, objectList,tempObj, point);

        tempArray.add(tempObj);

        add(b);

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        imageViewNum++;
    }
    public void newLInearLayout(){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();


        Layout_Linear b = new Layout_Linear(""+linearlayoutNum, objectList,tempObj);

        tempArray.add(tempObj);
        add(b);

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        linearlayoutNum++;
    }
    public void addMouseListner(){
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK)
                {
                    PopUpMenu menu = new PopUpMenu();
                    menu.show(e.getComponent(), e.getX(), e.getY());

                }
                else if(panel.getChoice()==4){
                    newButton(e.getPoint());
                    panel.setChoice(0);
                    isButton=false;
                    isRadioButton=false;
                    isImageView=false;
                }
                else if(panel.getChoice()==5){
                    newRadioButton(e.getPoint());
                    panel.setChoice(0);
                    isButton=false;
                    isRadioButton=false;
                    isImageView=false;
                }
                else if(panel.getChoice()==2){
                    newLInearLayout();
                    panel.setChoice(0);
                }
                else if(panel.getChoice()==6){
                    newImageView(e.getPoint());
                    panel.setChoice(0);
                    isButton=false;
                    isRadioButton=false;
                    isImageView=false;
                }
                else{
                    isButton=false;
                    isRadioButton=false;
                    isImageView=false;
                    panel.setChoice(0);

                }
                mouse_p = e.getLocationOnScreen();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isButton=false;
                isRadioButton=false;
                isImageView=false;
            }
        });
    }
    public void addMouseactionListner(Graphics g){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(panel.getChoice()==4){
                    x = e.getX(); y = e.getY();
                    isButton=true;
                }
                else if(panel.getChoice()==5){
                    x = e.getX(); y = e.getY();
                    isRadioButton=true;
                }
                else if(panel.getChoice()==6){
                    x = e.getX(); y = e.getY();
                    isImageView=true;
                }
            }
        });
    }

    public void getAttribute(JSONObject obj){

    }
    class PopUpMenu extends JPopupMenu {

        JMenuItem library;
        JMenuItem refresh;
        public PopUpMenu() {
            library = new JMenuItem("library");
            refresh = new JMenuItem("refresh");


            library.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Libray_Window a = new Libray_Window();
                }
            });

            refresh.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(getParent());
                    storyboard.drawActivity_temp();
                    sendData();
                }
            });
            add(library);
            add(refresh);
        }

    }

    class Libray_Window extends JFrame{
        JComboBox<String> checklibray;
        JButton okButton;
        JButton noButton;
        JLabel label = new JLabel("라이브러리 설정");
        public Libray_Window(){
            label.setSize(100,50);
            label.setLocation(10,0);

            checklibray = new JComboBox<String>();
            checklibray.addItem("error");
            checklibray.addItem("activity");
            checklibray.addItem("NONE");
            checklibray.setSize(230,50);
            checklibray.setLocation(30,45);

            if(objectJObject.containsKey("library")){

                checklibray.setSelectedItem(objectJObject.get("library"));
            }
            else
            {
                checklibray.setSelectedItem("NONE");
            }

            okButton = new JButton("OK");
            okButton.setSize(100,25);
            okButton.setLocation(50,110);

            noButton = new JButton("NO");
            noButton.setSize(100,25);
            noButton.setLocation(160,110);
            this.setUndecorated(true);      //title bar 제거
            setSize(300,150);
            setVisible(true);
            setLayout(null);
            setLocation(mouse_p.x,mouse_p.y);


            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(checklibray.getSelectedItem().equals("NONE")){
                        objectJObject.remove("library");
                    }
                    else
                    {
                        objectJObject.put("library", checklibray.getSelectedItem());
                    }
                    sendLibraryData((String)objectJObject.get("library"));
                    dispose();
                }
            });
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            add(label);
            add(checklibray);
            add(okButton);
            add(noButton);

            this.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {

                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    dispose();
                }
            });
        }

    }

    public void sendLibraryData(String library){
        String id_, activity_;

        id_=getId();
        activity_=activityName;

        System.out.println("id :"+id_+"  activity : "+activityName+ " library :" + library);
    }
}
