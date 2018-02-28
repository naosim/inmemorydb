package com.naosim.inmemorydb;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public class InMemoryDb {
    public static class Table<R> {
        private final LinkedHashMap<String, R> map = new LinkedHashMap<>();
        public final String tableName;
        private final Sequence idSequence = new Sequence();

        public String createId() {
            return idSequence.next(v -> tableName + v.toString());
        }

        public <T> T createId(Function<Long, T> factory) {
            return idSequence.next(factory);
        }

        public Table(String tableName) {
            this.tableName = tableName;
            Db.put(this);
        }

        public void clear() {
            map.clear();
        }

        public String insert(String id, R record) {
            if(map.containsKey(id)) {
                throw new RuntimeException("IDが重複しています");
            }
            map.put(id, record);
            return id;
        }

        public String deleteInsert(String id, R record) {
            if(map.containsKey(id)) {
                map.remove(id);
            }
            return insert(id, record);
        }

        public Optional<R> find(String id) {
            return Optional.ofNullable(map.get(id));
        }

        public Collection<R> all() {
            return map.values();
        }

        public void remove(String id) {
            map.remove(id);
        }

        @Override
        public String toString() {
            return tableName + '\n' + map.entrySet().stream().map(kv -> kv.getKey() + ":" + kv.getValue().toString()).collect(Collectors.joining("\n"));
        }
    }

    public static class Sequence {
        private long index = 1;
        public long next() {
            return index++;
        }
        public <T> T next(Function<Long, T> factory) {
            return factory.apply(next());
        }
    }

    public static class Db {
        private static final LinkedHashMap<String, Table> tableMap = new LinkedHashMap<>();
        static void put(Table onMemoryTable) {
            tableMap.put(onMemoryTable.tableName, onMemoryTable);
        }
        public static void clearAll() {
            tableMap.values().forEach(Table::clear);
        }
        public static Table get(String tableName) {
            return tableMap.get(tableName);
        }

        public static <R> Table<R> get(String tableName, Class<R> clazz) {
            return tableMap.get(tableName);
        }
    }
}
