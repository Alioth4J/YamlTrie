# YamlTrie
Converts yaml data to a trie tree.  

## Usage
### YamlTrieLoader
```java
InputStream is = this.getClass().getClassLoader().getResourceAsStream("config.yaml");
YamlTrieLoader loader = new YamlTrieLoader();
Trie trie = loader.load(is);
```

### Trie
Core methods:  
```java
public void insert(List<String> path, Object value);
public Optional<Object> search(List<String> path);
```

## Note
When existing a list, an `<index>` node will be added to the tree. So when searching something in the list, `"0"` as wildcard should be added to the path.  

Here is an example.  

config.yaml:  
```yaml
a:
  b:
    - c: d
    - e: f
```

The trie tree is like this:  
```
com.alioth4j.yamltrie.Trie{
a
  b
    0
      c
        [value] d
    1
      e
        [value] f
}
```

Search code:  
```java
Optional<Object> result = trie.search(List.of("a", "b", "0", "e"));                                   
result.ifPresentOrElse(v -> System.out.println("Found: " + v),
                       () -> System.out.println("Not found"));
```

The output is:  
```
Found: f
```
