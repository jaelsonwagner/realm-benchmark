package fr.nelaupe.benchmark;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static fr.nelaupe.benchmark.DatabaseOperation.INSERT;
import static fr.nelaupe.benchmark.DatabaseOperation.SEARCH;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by jaelson on 04/05/18.
 */

@Retention(SOURCE)
@StringDef({INSERT, SEARCH})
public @interface DatabaseOperation {
    String INSERT = "INSERT";
    String SEARCH = "SEARCH";
}