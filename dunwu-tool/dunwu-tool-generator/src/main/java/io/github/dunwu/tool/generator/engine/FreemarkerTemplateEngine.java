/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.dunwu.tool.generator.engine;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.dunwu.tool.generator.config.builder.ConfigBuilder;
import io.github.dunwu.tool.generator.util.FreemarkerHelper;

import java.io.IOException;
import java.util.Map;

/**
 * Freemarker 模板引擎实现文件输出
 *
 * @author nieqiurong
 * @since 2018-01-11
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

    private Configuration configuration;

    @Override
    public FreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        configuration = FreemarkerHelper.getDefaultConfiguration();
        return this;
    }

    @Override
    public String getMergeContent(Map<String, Object> objectMap, String templatePath)
        throws IOException, TemplateException {
        return FreemarkerHelper.getMergeContent(configuration, objectMap, templatePath);
    }

    @Override
    public void outputFile(Map<String, Object> objectMap, String templatePath, String outputFile)
        throws IOException, TemplateException {
        FreemarkerHelper.outputFile(configuration, objectMap, templatePath, outputFile);
        logger.debug(">>>> 创建文件：\n【模板】{}\n【文件】{}", templatePath, outputFile);
    }

    @Override
    public String getTemplatePath(String filePath) {
        return filePath + ".ftl";
    }

}
