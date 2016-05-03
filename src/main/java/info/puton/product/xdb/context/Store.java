package info.puton.product.xdb.context;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Created by taoyang on 4/8/16.
 */
public class Store {

    private BiMap<String,String> prefixMap;

    public String generatePrefix(Integer length){

        String prefix = RandomStringUtils.randomAlphabetic(1).toUpperCase()
                + RandomStringUtils.randomAlphanumeric(length-1).toUpperCase();

        if(!prefixMap.containsValue(prefix)){
            return prefix;
        }else{
            return generatePrefix(length);
        }

    }

    public String getMinPrefix(String fullPrefix){

        fullPrefix = fullPrefix.toUpperCase();

        if(!prefixMap.containsKey(fullPrefix)){

            String minPrefix = generatePrefix(3);

            prefixMap.put(fullPrefix, minPrefix);
        }

        return prefixMap.get(fullPrefix);

    }

    public String getFullPrefix(String minPrefix){

        return prefixMap.inverse().get(minPrefix);

    }

    public Store() {
        this.prefixMap = HashBiMap.create();
    }

    public static void main(String[] args) {
//        System.out.println(UUID.randomUUID().toString().substring(0,4));

        Store store = new Store();

//        for (int i = 0; i < 1000; i++) {
//            System.out.println(store.getMinPrefix(String.valueOf(i)));
//        }

        System.out.println(store.getMinPrefix("aaaaaaaa"));

        System.out.println(store.getMinPrefix("aaaaaaaa"));

        System.out.println(store.getMinPrefix("aaabbbbb"));

        System.out.println(store.getFullPrefix(store.getMinPrefix("aaaaaaaa")));

//        store.addPrefix("a");

    }

}
