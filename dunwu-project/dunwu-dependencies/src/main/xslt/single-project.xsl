<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="/">
    <xsl:copy-of select="projects/*[1]" />
    <xsl:copy-of select="*[local-name()='project']" />
  </xsl:template>
</xsl:stylesheet>
