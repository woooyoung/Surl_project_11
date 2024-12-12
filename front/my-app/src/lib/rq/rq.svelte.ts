import { goto } from '$app/navigation';
import createClient from 'openapi-fetch';
import type { paths } from '$lib/backend/apiV1/schema';
type Client = ReturnType<typeof createClient<paths>>;
const client: Client = createClient<paths>({
	baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
	credentials: 'include'
});
class Rq {
	public goto(url: string) {
		goto(url);
	}
	public getClient() {
		return client;
	}
}
const rq = new Rq();
export default rq;