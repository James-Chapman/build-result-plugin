package jenkins.plugins.build_result;

import hudson.util.ListBoxModel;

/**
 * @author James Chapman
 */
public enum ResultType {

    SUCCESS,
    UNSTABLE,
    FAILURE,
    ABORTED,
    NOT_BUILT;

//    private final String value;
//
//    ResultType(String value) {
//        this.value = value;
//    }
//
//    public String getValue() {
//        return value;
//    }

    public static ListBoxModel getFillItems() {
        ListBoxModel items = new ListBoxModel();
        for (ResultType resultType : values()) {
            items.add(resultType.name());
        }
        return items;
    }

}
