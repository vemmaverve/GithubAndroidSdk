package com.alorma.github.sdk.services.repo;

import android.content.Context;
import android.support.annotation.StringDef;
import com.alorma.github.sdk.bean.dto.response.Repo;
import com.alorma.github.sdk.bean.info.RepoInfo;
import com.alorma.github.sdk.services.client.GithubListClient;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import retrofit.RestAdapter;

/**
 * Created by a557114 on 05/09/2015.
 */
public class GetForksClient extends GithubListClient<List<Repo>> {

    private final Context context;
    private final RepoInfo repoInfo;
    private final int page;
    private String sort = null;

    public GetForksClient(Context context, RepoInfo repoInfo) {
        this(context, repoInfo, 0);
    }

    public GetForksClient(Context context, RepoInfo repoInfo, int page) {
        super(context);
        this.context = context;
        this.repoInfo = repoInfo;
        this.page = page;
    }
    @Override
    protected void executeService(RestAdapter restAdapter) {
        RepoService repoService = restAdapter.create(RepoService.class);
        if (page == 0) {
            repoService.listForks(repoInfo.owner, repoInfo.name, sort, this);
        } else {
            repoService.listForks(repoInfo.owner, repoInfo.name, sort, page, this);
        }
    }

    @Override
    protected List<Repo> executeServiceSync(RestAdapter restAdapter) {
        RepoService repoService = restAdapter.create(RepoService.class);
        if (page == 0) {
            return repoService.listForks(repoInfo.owner, repoInfo.name, sort);
        } else {
            return repoService.listForks(repoInfo.owner, repoInfo.name, sort, page);
        }
    }

    // newest, oldest, stargazers

    public static final String NEWEST = "newest";
    public static final String OLDEST = "oldest";
    public static final String STARGAZERS = "stargazers";

    @StringDef({NEWEST, OLDEST, STARGAZERS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SORT{}

    public void setSort(@SORT String sort) {
        this.sort = sort;
    }
}
