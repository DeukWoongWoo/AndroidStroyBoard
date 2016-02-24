package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.DtatTransferObject.XmlDTO;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-02-23.
 */
public class EventLibCreate {
    private final int IMPLEMENT = 1;
    private final int LISTENER = 2;
    private final int LOCAL = 3;
    private final int FUNC = 4;

    private String id;
    private String xml;

    private PsiMethod resumeMethod;

    public EventLibCreate(String id, String xml){
        this.id = id;
        this.xml = xml;
    }

    public void create(){
        XmlDTO xmlDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectXml("xmlName='R.layout." + xml + "'")).get(0).getXml(0);
        int xmlId = xmlDTO.getNum();
        ComponentDTO component = DatabaseManager.getInstance().selectToJava(table -> table.selectComponent("xmlId=" + xmlId, "xmlName='R.id." + id + "'")).get(0).getComponent(0);
        int componentId = component.getNum();
        EventDTO eventDTO = DatabaseManager.getInstance().selectToJava(table -> table.selectEvent("componentId=" + componentId)).get(0).getEvent(0);
        File file = new File(DatabaseManager.getInstance().selectToJava(table -> table.selectJava("num=" + xmlDTO.getJavaId())).get(0).getPath());
        PsiJavaFile psiJavaFile = (PsiJavaFile) PsiManager.getInstance(SharedPreference.ACTIONEVENT.getData().getProject()).findFile(LocalFileSystem.getInstance().findFileByIoFile(file));

        PsiClass mainClass = psiJavaFile.getClasses()[0];

        ElementFactory elementFactory = new ElementFactory();

        new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                mainClass.add(elementFactory.createPsiField("private UserLiporter userLiporterEvent = new CatchEvent();",mainClass));
            }
        }.execute();

        if (eventDTO == null) {
            new WriteCommandAction.Simple(psiJavaFile.getProject(), psiJavaFile.getContainingFile()) {
                @Override
                protected void run() throws Throwable {
                    StringBuilder clickStr = new StringBuilder("public void On" + id + "Clicked(View view){\n");
                    clickStr.append("userLiporterEvent.get("+ id +");\n");
                    clickStr.append("}\n");

                    mainClass.add(elementFactory.createPsiMethod(clickStr.toString(), mainClass));
                }
            }.execute();
        }else{
            int type = eventDTO.getType();
            if (type == IMPLEMENT) {
                for(PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()){
                    if(psiMethod.getName().equals("onClick")){
                        new WriteCommandAction.Simple(psiJavaFile.getProject(),psiJavaFile.getContainingFile()){
                            @Override
                            protected void run() throws Throwable {
                                psiMethod.getBody().add(elementFactory.createPsiStatement("userLiporterEvent.get("+ id +");\n",psiMethod));
                            }
                        }.execute();
                    }
                }
            }else if(type == LOCAL){
                for(PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()) {
                    PsiCodeBlock body = psiMethod.getBody();
                    for(PsiStatement statement : body.getStatements()) {
                        Pattern pattern = Pattern.compile(component.getName()+".setOnClickListener");
                        Matcher matcher = pattern.matcher(statement.getText());

                        if(matcher.find()){
                            PsiElement[] element = ((PsiExpressionStatement) statement).getExpression().getChildren();
                            PsiMethod method = (PsiMethod) element[1].getChildren()[1].getChildren()[3].getChildren()[5];

                            new WriteCommandAction.Simple(method.getProject(), method.getContainingFile()) {
                                @Override
                                protected void run() throws Throwable {
                                    method.getBody().add(elementFactory.createPsiStatement("userLiporterEvent.get("+ id +");\n",method));
                                }
                            }.execute();
                        }
                    }
                }
            }
        }

        for(PsiMethod psiMethod : psiJavaFile.getClasses()[0].getMethods()){
            if(psiMethod.getName().equals("onResume")) resumeMethod = psiMethod;
        }

        new WriteCommandAction.Simple(mainClass.getProject(), mainClass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                if(resumeMethod != null){
                    resumeMethod.getBody().add(elementFactory.createPsiStatement("userLiporterEvent.set(this);",mainClass));
                }else{
                    StringBuilder builder = new StringBuilder("@Override\n");
                    builder.append("protected void onResume() {\n");
                    builder.append("super.onResume();\n");
                    builder.append("userLiporterEvent.set(this);\n");
                    builder.append("}\n");

                    mainClass.add(elementFactory.createPsiMethod(builder.toString(),mainClass));
                }
            }
        }.execute();

        syncProject();
    }

    public void remove(){

    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null, null).executeAll();
    }
}