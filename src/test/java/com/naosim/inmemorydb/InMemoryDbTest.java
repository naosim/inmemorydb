package com.naosim.inmemorydb;

import junit.framework.TestCase;

import static org.junit.Assert.*;

public class InMemoryDbTest extends TestCase {
    public InMemoryDbTest(String name){
        super(name);
    }

    public void test_access_from_db() {
        // create table
        InMemoryDb.Table<String> table = new InMemoryDb.Table<>("sample");
        table.insert(table.createId(), "hoge");
        table.insert(table.createId(), "foo");
        InMemoryDb.Table<String> table2 = new InMemoryDb.Table<>("sample2");
        table2.insert(table2.createId(),  "hoge2");
        table2.insert(table2.createId(), "foo2");
        table2.insert(table2.createId(), "bar2");

        // access from db
        assert InMemoryDb.Db.get("sample").all().size() == 2 : "sample table";
        assert InMemoryDb.Db.get("sample2").all().size() == 3 : "sample2 table";

        // clear
        InMemoryDb.Db.clearAll();

        // clear tables
        assert InMemoryDb.Db.get("sample").all().size() == 0 : "sample removed";
        assert InMemoryDb.Db.get("sample").all().size() == 0 : "sample2 removed";
    }

}