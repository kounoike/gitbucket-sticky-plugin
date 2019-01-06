import gitbucket.core.plugin.PluginRegistry
import gitbucket.core.service.SystemSettingsService
import io.github.gitbucket.solidbase.model.Version
import javax.servlet.ServletContext

class Plugin extends gitbucket.core.plugin.Plugin {
  override val pluginId: String = "sticky"
  override val pluginName: String = "Sticky Plugin"
  override val description: String = "Enable sticky display for issue/pr/wikipage title"
  override val versions: List[Version] = List(new Version("1.0.0"))

  override val assetsMappings = Seq("/sticky" -> "/assets")

  override def javaScripts(registry: PluginRegistry, context: ServletContext, settings: SystemSettingsService.SystemSettings): Seq[(String, String)] = {
    val jsPath = settings.baseUrl.getOrElse(context.getContextPath) + "/plugin-assets/sticky/vendor/jquery.sticky.js"
    val cssPath = settings.baseUrl.getOrElse(context.getContextPath) + "/plugin-assets/sticky/sticky.css"
    Seq(".*" ->
      (s"""</script><script src="${jsPath}"></script><link href="${cssPath}" rel="stylesheet">""" +
      """<script>
        |$(document).ready(function(){
        |    $('h1.body-title').sticky({zIndex: 1})
        |})
      """.stripMargin))
  }
}
