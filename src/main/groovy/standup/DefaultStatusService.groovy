package standup

import ratpack.exec.Promise

class DefaultStatusService implements StatusService {
    private List<Status> storage = []

    @Override
    Promise<Status> create(Status newStatus) {
        storage << newStatus

        return Promise.sync { newStatus }
    }

    @Override
    Promise<List<Status>> list() {
        return Promise.sync { storage }
    }
}
