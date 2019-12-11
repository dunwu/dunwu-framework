<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:m="http://maven.apache.org/POM/4.0.0" version="2.0"
  exclude-result-prefixes="m">
  <xsl:output xmlns:xslt="http://xml.apache.org/xslt" method="xml" encoding="utf-8"
    indent="yes" xslt:indent-amount="4" />
  <xsl:strip-space elements="*" />
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()" />
    </xsl:copy>
  </xsl:template>
  <xsl:template
    match="/m:project/m:build/m:pluginManagement/m:plugins/m:plugin/m:dependencies/m:dependency/m:version/text()[. = '${revision}']">
    <xsl:value-of select="/m:project/m:version/text()" />
  </xsl:template>
  <xsl:template match="/m:project/m:groupId" />
  <xsl:template match="/m:project/m:version" />
  <xsl:template match="/m:project/m:build/m:plugins" />
  <xsl:template match="/m:project/m:build/m:pluginManagement" />
  <xsl:template match="/m:project/m:licenses" />
</xsl:stylesheet>
