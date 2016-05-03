package info.puton.product.xdb.converter;

/**
 * Created by taoyang on 4/7/16.
 */
public class KeyConverter{


    public static String toUnderline(String dotFormat) {

        dotFormat = dotFormat.replaceAll("\\.", "_").toUpperCase();

        return dotFormat;

    }

    public static void main(String[] args) {

        System.out.println(toUnderline("AA.BB.CC.DD"));

    }

}
