package com.example.plugin;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by fangfuming
 * 2020/11/15
 */
public class JiaguTask extends DefaultTask {


    private final File apk;
    private final JiaguExt jiaguExt;

    @Inject
    public JiaguTask(File apk, JiaguExt jiaguExt){
        setGroup("jiagu");
        this.apk = apk;
        this.jiaguExt = jiaguExt;
    }


    @TaskAction
    public void doThings(){
        //1.登录360加固
        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                execSpec.commandLine("java","-jar",jiaguExt.getJiaguToolPath(),"-login",jiaguExt.getUserName(),jiaguExt.getUserPwd());
            }
        });
        //2.签名
        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                //签名地址 签名密码 别名 别名密码
                execSpec.commandLine("java","-jar",
                        jiaguExt.getJiaguToolPath(),"-importsign",
                        jiaguExt.getKeyStorePath(),
                        jiaguExt.getKeyStorePass(),
                        jiaguExt.getKeyStoreKeyAlias(),
                        jiaguExt.getKeyStoreKeyAliasPwd());
            }
        });

        //3.加固
        getProject().exec(new Action<ExecSpec>() {
            @Override
            public void execute(ExecSpec execSpec) {
                //签名地址 签名密码 别名 别名密码
                execSpec.commandLine("java","-jar",
                        jiaguExt.getJiaguToolPath(),"-jiagu",
                        apk.getAbsolutePath(),
                        apk.getParent(),"-autosign");
            }
        });

        System.out.println(apk.getName()+apk.getAbsolutePath());

    }





}
