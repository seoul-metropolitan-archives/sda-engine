/*
 * Copyright (c) 2017. RMSoft Co.,Ltd. All rights reserved
 *
 */

package rmsoft.ams.seoul.workflow;

import io.onsemiro.core.context.AppContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import rmsoft.ams.seoul.common.domain.AcUser;
import rmsoft.ams.seoul.common.repository.AcUserRepository;

import java.util.List;

/**
 * The type Process test.
 */
@Service
public class ProcessTest implements Runnable {
    private String batchId = "";
    private String stopBatchId = "";

    @Autowired
    private AcUserRepository acUserRepository;

    private String message = "";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ProcessTest test = new ProcessTest();
        Thread thread = new Thread(test);
        thread.start();
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets batch id.
     *
     * @param batchId the batch id
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        acUserRepository = AppContextManager.getBean(AcUserRepository.class);

        List<AcUser> acUserList = acUserRepository.findAll();

        ThreadPoolTaskExecutor threadPoolTaskExecutor = AppContextManager.getBean(ThreadPoolTaskExecutor.class);


        try {
            int threadIndex = 0;

            while (!batchId.equals(stopBatchId)) {
                long id = Thread.currentThread().getId();
                String name = Thread.currentThread().getName();

            /*for (int i = 0; i < acUserList.size(); i++) {
                System.out.println("Thread with [" + id + "] : " + name);
                System.out.println("[" + message + "]" + acUserList.get(i).getUserNm());
                Thread.sleep(1000);
            }*/
                System.out.println("Thread Active Count [" + threadPoolTaskExecutor.getActiveCount() + "]");
                System.out.println("Thread with [" + id + "] : " + name);
                System.out.println("[" + threadIndex + "]" + message);

                threadIndex++;

                Thread.sleep(1000);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Thread is Ended");
        }
    }

    /**
     * Stop process.
     *
     * @param batchId the batch id
     */
    public void stopProcess(String batchId) {
        this.stopBatchId = batchId;
    }
}
