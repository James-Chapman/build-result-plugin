/*******************************************************************************
 * Copyright   : MIT License
 * Author      : James Chapman build-result-plugin@mtbfr.co.uk
 * Date        : 17/02/2015
 * Description : Build Result Plugin
 *******************************************************************************/
package jenkins.plugins.build_result;

import hudson.*;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

import hudson.util.ListBoxModel;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * BuildResult Class
 */
public class BuildResult extends Builder {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuildResult.class);

    private final String buildResultType;

    @DataBoundConstructor
    public BuildResult(String buildResultType) {
        this.buildResultType = Util.fixEmpty(buildResultType);
    }

    public String getBuildResultType() {
        return buildResultType;
    }

    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
        final PrintStream logger = listener.getLogger();

        if(buildResultType != null && !buildResultType.isEmpty()) {
            if (buildResultType.equals("SUCCESS")) build.setResult(Result.SUCCESS);
            else if (buildResultType.equals("UNSTABLE")) build.setResult(Result.UNSTABLE);
            else if (buildResultType.equals("FAILURE")) build.setResult(Result.FAILURE);
            else if (buildResultType.equals("ABORTED")) build.setResult(Result.ABORTED);
            else if (buildResultType.equals("NOT_BUILT")) build.setResult(Result.NOT_BUILT);
            else build.setResult(Result.SUCCESS);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        //private List<ResultType> buildResultTypes = new ArrayList<ResultType>();

        public DescriptorImpl() {
            load();
        }

//        public List<ResultType> getResultTypes() {
//            return buildResultTypes;
//        }
//
//        public void setResultTypes(List<ResultType> resultTypeList) {
//            this.buildResultTypes = resultTypeList;
//        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Set build status";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws
                FormException {
            req.bindJSON(this, formData);
            save();
            return true;
        }

        public ListBoxModel doFillDefaultBuildResultTypeItems() {
            ListBoxModel items = ResultType.getFillItems();
            return items;
        }

        public ListBoxModel doFillBuildResultTypeItems() {
            ListBoxModel items = ResultType.getFillItems();
            return items;
        }

    }
}
