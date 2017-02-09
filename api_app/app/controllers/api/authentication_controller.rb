module Api
  class AuthenticationController < ApplicationController
    skip_before_action :authenticate_request

    def authenticate
      command = AuthenticateUser.call(params[:email], params[:password])
      @action = "AUTHENTICATE"
      if command.success?
        render json: { action: @type, id: command.get_id, auth_token: command.result }
      else
        render json: { error: command.errors }, status: :unauthorized
      end
    end
  end
end
