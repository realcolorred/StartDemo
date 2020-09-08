package com.example.demo.generator;

/**
 * Created by new on 2020/9/8.
 */

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * mysql 代码生成器
 * </p>
 */
public class MybatisGenerator {
    // 全局配置
    private final static String OUTPUT_DIR      = "\\src\\main\\java";// 生成文件的输出目录
    private final static String OUTPUT_DIR_XML  = "\\src\\main\\resources";// 生成文件的输出目录
    private final static String AUTHOR          = "realcolorred";// 开发人员
    // 数据源配置
    private final static String DATABASE_IP     = "127.0.0.1:3306";// 数据库id
    private final static String DATABASE_NAME   = "fgo";// 数据库名称
    // 包配置
    private final static String PARENT          = "com.example.demo.dao";// 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
    private final static String MODULE_NAME     = "sourceCompany";// 父包模块名
    // 自定义基类
    private final static String SuperEntity     = "com.eshore.crm.csc.common.base.BaseEntity";// 所有实体的基类(全类名)
    private final static String SuperController = "com.eshore.crm.csc.common.base.BaseController";// 所有控制器的基类(全类名)

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + OUTPUT_DIR);
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setFileOverride(false);//覆盖目录
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + DATABASE_IP + "/" + DATABASE_NAME + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC");
        // dsc.setSchemaName("public");
        // dsc.setDriverName("com.mysql.jdbc.Driver");// JDK7
        dsc.setDriverName("com.mysql.jdbc.Driver");// JDK8
        dsc.setUsername("root");
        dsc.setPassword("root");
        // 自定义数据库表字段类型转换【可选】
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                //System.out.println("转换类型：" + fieldType);
                if ("datetime".equals(fieldType) || "timestamp".equals(fieldType)) {
                    //datetime转换成java.util.Date类型
                    globalConfig.setDateType(DateType.ONLY_DATE);
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });

        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //        pc.setModuleName(scanner("模块名"));
        pc.setModuleName(MODULE_NAME);
        pc.setParent(PARENT);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + OUTPUT_DIR_XML + "/" + PARENT.replaceAll("\\.", "/") + "/" + MODULE_NAME + "/mapping/" +
                    tableInfo.getEntityName() +
                    "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController("");//不生成controller；
        templateConfig.setService("");
        templateConfig.setServiceImpl("");
        mpg.setTemplate(templateConfig.setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass(SuperEntity);
        strategy.setEntityLombokModel(true);//【实体】是否为lombok模型
        //strategy.setSuperControllerClass(SuperController);
        strategy.setInclude(scanner("(多表用逗号分隔)表名"));
        //strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * <p>读取控制台内容</p>
     */
    public static String[] scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtil.isNotEmpty(ipt)) {
                return ipt.split(",");
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
