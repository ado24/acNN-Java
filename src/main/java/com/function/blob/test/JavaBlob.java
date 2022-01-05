package com.function.blob.test;

import com.google.gson.Gson;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.*;
import com.model.ScoreContainer;

import java.io.*;


/**
 * Azure Functions with Azure Blob trigger.
 */
public class JavaBlob {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path. The blob contents are provided as input to this function.
     */
    @FunctionName("blob-java-test")
    @StorageAccount("AzureWebJobsStorage")
    public void run(
        @BlobTrigger(name = "content", path = "samples-workitems/{name}", dataType = "binary") char[] content,
        @BindingName("name") String name,
        final ExecutionContext context
    ) {
        BufferedReader br = new BufferedReader(new CharArrayReader(content));
        String input = "";

        context.getLogger().info("Start Process");

        try {
            while ((input = br.readLine()) != null) {
                context.getLogger().info("Value passed in: " + input);
                ScoreContainer in = new Gson().fromJson(input, ScoreContainer.class);
                context.getLogger().info("Value stored: " + in.toJsonString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: " + content.length + " Bytes");
    }
}
