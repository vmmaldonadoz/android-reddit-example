package com.thirdmono.reddit.presentation;

/**
 * Base presenter in the MVP pattern.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public interface BasePresenter {

    void resume();

    void pause();

    void destroy();

    void setView(BaseView view);

}
