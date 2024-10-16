import {createApp} from "vue";
import App from "./App.vue";
import {setupDirectives} from "./directives";
import {setupAssets, setupLazy, setupMasonry, setupMdPreview, setupViewer} from "./plugins";
import {setupRouter} from "./router";
import {setupStore} from "./store";
import {titleChange} from "./utils/title";
import {a} from "./utils/antiDebug"

async function setupApp() {
	setupAssets();

	const app = createApp(App);

	setupStore(app);

	setupDirectives(app);

	setupLazy(app);

	setupMdPreview(app);

	setupMasonry(app);

	setupViewer(app);

	await setupRouter(app);

	// 初始化动态标题功能
	titleChange();

	app.mount("#app");
}

setupApp();
a();
