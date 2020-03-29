package io.mycat.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import io.mycat.dao.util.SQLUtil;

public class SQLUtilTest {

    @Test
    public void fillDynaQueryTemplate() {
        String sql = "select ddd,ddd,ccc ,ddd,eeee from table a  jion b on a.id=b.pid where ${t1.field like :field1 AND} tabl1.$id=table2.parid and table2.name like '$}' ${OR t2.field= :field2} ${AND t3.field != :field3 }";
        List<String> tokens=new ArrayList<>();
        tokens.add("${t1.field like :field1 AND}");
        tokens.add("${OR t2.field= :field2}");
        tokens.add("${AND t3.field != :field3 }");
        String theRst="select ddd,ddd,ccc ,ddd,eeee from table a  jion b on a.id=b.pid where xxxx tabl1.$id=table2.parid and table2.name like '$}' xxxx xxxx";
        List<String> mytokens=new ArrayList<>();
        String rest = SQLUtil.fillDynaQueryTemplate(sql, (t) -> {
            System.out.println("find token " + t);
            mytokens.add(t);
            return "xxxx";
        });
        assertEquals(tokens, mytokens);
        assertEquals(theRst, rest);
        System.out.println(rest);
    }

    @Test
    public void fillDynaQueryTemplate2() {
        String sql = "${AND phone like :phone }  ${OR sex= :sex} ${AND totlePrice != :totlePrice }";
        List<String> tokens=new ArrayList<>();
        tokens.add("${AND phone like :phone }");
        tokens.add("${OR sex= :sex}");
        tokens.add("${AND totlePrice != :totlePrice }");
        String theRst="xxxx  xxxx xxxx";
        List<String> mytokens=new ArrayList<>();
        String rest = SQLUtil.fillDynaQueryTemplate(sql, (t) -> {
            System.out.println("find token " + t);
            mytokens.add(t);
            return "xxxx";
        });
        assertEquals(tokens, mytokens);
        assertEquals(theRst, rest);
        System.out.println(rest);
    }
}