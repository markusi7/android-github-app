package markusi.githubapp.data.models.api;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({
        PaginationLinkType.NEXT,
        PaginationLinkType.LAST,
        PaginationLinkType.FIRST,
        PaginationLinkType.PREVIOUS
})
@Retention(RetentionPolicy.SOURCE)
public @interface PaginationLinkType {

    String NEXT = "next";

    String LAST = "last";

    String FIRST = "first";

    String PREVIOUS = "previous";

}
