package io.github.yesalam.acquaint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.yesalam.acquaint.Pojo.Card.CasePojo;

/**
 * Created by yesalam on 13-06-2017.
 */

public interface WaitingForData {
    void passData(List<? extends Object> data);
}
