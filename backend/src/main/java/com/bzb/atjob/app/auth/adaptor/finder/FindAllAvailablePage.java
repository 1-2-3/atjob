package com.bzb.atjob.app.auth.adaptor.finder;

import com.bzb.atjob.app.auth.core.page.model.Page;
import java.util.List;

@FunctionalInterface
public interface FindAllAvailablePage {
  List<Page> findAllAvailablePage();
}
