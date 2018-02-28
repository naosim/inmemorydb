package com.naosim.inmemorydb;

import junit.framework.TestCase;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTableTest extends TestCase {
    public InMemoryTableTest(String name){
        super(name);
    }

    public void test_insert() {
        InMemoryDb.Table<String> table = new InMemoryDb.Table<>("sample");
        table.insert(table.createId(), "hoge");
        table.insert(table.createId(), "foo");
        List<String> act = table.all().stream().collect(Collectors.toList());
        assert act.get(0) == "hoge";
        assert act.get(1) == "foo";
    }

    public void test_clear() {
        InMemoryDb.Table<String> table = new InMemoryDb.Table<>("sample");
        table.insert(table.createId(), "hoge");
        table.insert(table.createId(), "foo");
        assert table.all().size() == 2 : "records exist";

        table.clear();

        assert table.all().size() == 0 : "records removed";
    }
}
