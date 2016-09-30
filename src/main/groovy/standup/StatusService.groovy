package standup

import ratpack.exec.Promise

interface StatusService {
    Promise<Status> create(Status newStatus)

    Promise<List<Status>> list()
}
